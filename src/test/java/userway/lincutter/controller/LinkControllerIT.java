package userway.lincutter.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import userway.lincutter.config.MongoConfig;
import userway.lincutter.model.Link;
import userway.lincutter.service.LinkService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LinkController.class)
public class LinkControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LinkService linkService;
    @MockBean
    private MongoConfig mongoConfig;

    @Test
    public void shortenLink() throws Exception {
        String originalUrl = "http://original.url";
        Link mockLink = new Link();
        mockLink.setOriginalUrl(originalUrl);
        mockLink.setShortUrl("http://short.link/test");

        when(linkService.shortenLink(originalUrl)).thenReturn(mockLink);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/links/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(originalUrl))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("http://short.link/test"));

        verify(linkService, times(1)).shortenLink(originalUrl);
        verifyNoMoreInteractions(linkService);
    }

    @Test
    public void resolveLink() throws Exception {
        String shortUrl = "http://short.link/test";
        String originalUrl = "http://original.url";

        when(linkService.getLinkByShortUrl(shortUrl)).thenReturn(originalUrl);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/links/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shortUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(originalUrl));

        verify(linkService, times(1)).getLinkByShortUrl(shortUrl);
        verifyNoMoreInteractions(linkService);
    }

    @Test
    public void resolveLinkNotFound() throws Exception {
        String shortUrl = "http://short.link/nonexistent";

        when(linkService.getLinkByShortUrl(shortUrl)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/links/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shortUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Shortened URL not found"));

        verify(linkService, times(1)).getLinkByShortUrl(shortUrl);
        verifyNoMoreInteractions(linkService);
    }

    @Test
    public void listAllLinks() throws Exception {
        List<Link> mockLinks = new ArrayList<>();
        when(linkService.getAllLinks()).thenReturn(mockLinks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/links/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));

        verify(linkService, times(1)).getAllLinks();
        verifyNoMoreInteractions(linkService);
    }
}
