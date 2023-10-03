package userway.lincutter.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import userway.lincutter.model.Link;
import userway.lincutter.service.LinkService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkControllerTest {

    @InjectMocks
    private LinkController linkController;
    @Mock
    private LinkService linkService;


    @Test
    void shortenLink() {
        String originalUrl = "http://original.url";
        Link mockLink = new Link();
        mockLink.setOriginalUrl(originalUrl);
        mockLink.setShortUrl("http://short.link/test");

        when(linkService.shortenLink(originalUrl)).thenReturn(mockLink);

        ResponseEntity<String> responseEntity = linkController.shortenLink(originalUrl);

        verify(linkService, times(1)).shortenLink(originalUrl);
        verifyNoMoreInteractions(linkService);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("http://short.link/test", responseEntity.getBody());
    }


    @Test
    void resolveLink() {
        // Input data
        String shortUrl = "http://short.link/test";
        String originalUrl = "http://original.url";

        when(linkService.getLinkByShortUrl(shortUrl)).thenReturn(originalUrl);

        ResponseEntity<String> responseEntity = linkController.resolveLink(shortUrl);

        verify(linkService, times(1)).getLinkByShortUrl(shortUrl);
        verifyNoMoreInteractions(linkService);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(originalUrl, responseEntity.getBody());
    }

    @Test
    public void resolveLinkNotFound() {
        String shortUrl = "http://short.link/nonexistent";

        when(linkService.getLinkByShortUrl(shortUrl)).thenReturn(null);

        ResponseEntity<String> responseEntity = linkController.resolveLink(shortUrl);

        verify(linkService, times(1)).getLinkByShortUrl(shortUrl);
        verifyNoMoreInteractions(linkService);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Shortened URL not found", responseEntity.getBody());
    }

    @Test
    void listAllLinks() {
        List<Link> mockLinks = new ArrayList<>();
        when(linkService.getAllLinks()).thenReturn(mockLinks);

        ResponseEntity<List<Link>> responseEntity = linkController.listAllLinks();

        verify(linkService, times(1)).getAllLinks();
        verifyNoMoreInteractions(linkService);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockLinks, responseEntity.getBody());
    }
}