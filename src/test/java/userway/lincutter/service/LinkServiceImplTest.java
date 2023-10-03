package userway.lincutter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import userway.lincutter.model.Link;
import userway.lincutter.repository.LinkRepository;
import userway.lincutter.utils.RedisManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;
    @Mock
    private RedisManager redisManager;

    private LinkService linkService;
    private Link testLink;
    private String originalUrl;
    private String shortUrl;

    @BeforeEach
    void setUp() {
        linkService = new LinkServiceImpl(linkRepository, redisManager);

        testLink = new Link();
        originalUrl = "originalUrl";
        testLink.setOriginalUrl(originalUrl);
        shortUrl = "shortUrl";
        testLink.setShortUrl(shortUrl);
    }

    @Test
    void shortenLinkFromDB() {
        when(linkRepository.findByOriginalUrl(anyString())).thenReturn(testLink);

        Link link = linkService.shortenLink(originalUrl);

        verify(linkRepository, times(1)).findByOriginalUrl(anyString());
        verify(linkRepository, never()).save(any(Link.class));

        assertNotNull(link);
        assertEquals(testLink, link);
        assertEquals(testLink.getOriginalUrl(), link.getOriginalUrl());
        assertEquals(testLink.getShortUrl(), link.getShortUrl());
    }

    @Test
    void shortenLinkToDB() {
        when(linkRepository.findByOriginalUrl(anyString())).thenReturn(null);
        when(linkRepository.save(any(Link.class))).thenReturn(testLink);

        Link savedLink = linkService.shortenLink(originalUrl);

        verify(linkRepository, times(1)).findByOriginalUrl(anyString());
        verify(linkRepository, times(1)).save(any(Link.class));

        assertNotNull(savedLink);
        assertEquals(testLink, savedLink);
        assertEquals(testLink.getOriginalUrl(), savedLink.getOriginalUrl());
        assertEquals(testLink.getShortUrl(), savedLink.getShortUrl());
    }

    @Test
    void getLinkByShortUrlFromDB() {
        when(linkRepository.findByShortUrl(anyString())).thenReturn(testLink);

        String original = linkService.getLinkByShortUrl(shortUrl);

        verify(redisManager, times(1)).isInRedis(anyString());
        verify(redisManager, never()).getFromRedis(anyString());
        verify(linkRepository, times(1)).findByShortUrl(anyString());

        assertEquals(originalUrl, original);

    }

    @Test
    void getLinkByShortUrlFromRedis() {
        when(redisManager.isInRedis(anyString())).thenReturn(true);
        when(redisManager.getFromRedis(anyString())).thenReturn(originalUrl);

        String original = linkService.getLinkByShortUrl(shortUrl);


        verify(redisManager, times(1)).isInRedis(anyString());
        verify(redisManager, times(1)).getFromRedis(anyString());
        verify(linkRepository, never()).findByShortUrl(anyString());

        assertEquals(originalUrl, original);
    }

    @Test
    void getAllLinks() {
        List<Link> fakeList = List.of(testLink);
        when(linkRepository.findAll()).thenReturn(fakeList);

        List<Link> allLinks = linkService.getAllLinks();

        verify(linkRepository, times(1)).findAll();

        assertNotNull(allLinks);
        assertEquals(fakeList, allLinks);

        assertEquals(fakeList.size(), allLinks.size());
        assertEquals(fakeList.get(0), allLinks.get(0));
        assertEquals(fakeList.get(0).getOriginalUrl(), allLinks.get(0).getOriginalUrl());
        assertEquals(fakeList.get(0).getShortUrl(), allLinks.get(0).getShortUrl());
    }
}