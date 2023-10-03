package userway.lincutter.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userway.lincutter.exception.LinkNotFoundException;
import userway.lincutter.model.Link;
import userway.lincutter.repository.LinkRepository;
import userway.lincutter.utils.RedisManager;
import userway.lincutter.utils.UrlGenerator;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final RedisManager redisManager;

    @Autowired
    public LinkServiceImpl(LinkRepository linkRepository, RedisManager redisManager) {
        this.linkRepository = linkRepository;
        this.redisManager = redisManager;
    }

    @Override
    public Link shortenLink(String originalUrl) {
        Link linkInDB = linkRepository.findByOriginalUrl(originalUrl);
        if (linkInDB != null) {
            return linkInDB;
        }

        String BASE_URL = "http://shrt.lnk/";
        String shortUrl = BASE_URL + generateRandomShortUrl(originalUrl);

        Link link = new Link();
        link.setOriginalUrl(originalUrl);
        link.setShortUrl(shortUrl);

        redisManager.setLinkToRedis(link);
        return linkRepository.save(link);
    }

    @Override
    public String getLinkByShortUrl(String shortUrl) {
        if (redisManager.isInRedis(shortUrl)) {
            return redisManager.getFromRedis(shortUrl);
        }
        Link linkFromRepo = linkRepository.findByShortUrl(shortUrl);

        return linkFromRepo != null ? linkFromRepo.getOriginalUrl() : null;
    }

    @Override
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    private String generateRandomShortUrl(String originalUrl) {
        String shortUrl;
        int maxAttempts = 3;
        int attempt = 0;

        do {
            if (attempt >= maxAttempts) {
                throw new LinkNotFoundException("Failed to generate a unique short URL after " + maxAttempts + " attempts.");
            }
            shortUrl = UrlGenerator.generateShortUrl(originalUrl);
            attempt++;
        } while (linkRepository.existsByShortUrl(shortUrl));

        return shortUrl;
    }
}
