package userway.lincutter.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import userway.lincutter.model.Link;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataMongoTest
class LinkRepositoryTest {

    @Autowired
    private LinkRepository linkRepository;
    private String originalUrl;
    private String shortUrl;


    @BeforeEach
    void before() {
        Link testLink = new Link();

        originalUrl = "originalUrl";
        testLink.setOriginalUrl(originalUrl);
        shortUrl = "qwerty";
        testLink.setShortUrl(shortUrl);

        linkRepository.save(testLink);
    }

    @AfterEach
    void tearDown() {
        linkRepository.deleteAll();
    }

    @Test
    void findByShortUrl() {
        Link linkFromRepo = linkRepository.findByShortUrl(shortUrl);

        assertNotNull(linkFromRepo);
        assertEquals(originalUrl, linkFromRepo.getOriginalUrl());
        assertEquals(shortUrl, linkFromRepo.getShortUrl());
    }

    @Test
    void findByOriginalUrl() {
        Link linkFromRepo = linkRepository.findByOriginalUrl(originalUrl);

        assertNotNull(linkFromRepo);
        assertEquals(originalUrl, linkFromRepo.getOriginalUrl());
        assertEquals(shortUrl, linkFromRepo.getShortUrl());
    }

    @Test
    void existsByShortUrl() {
        boolean exists = linkRepository.existsByShortUrl(shortUrl);

        assertTrue(exists);
    }
}