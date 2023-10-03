package userway.lincutter.service;


import userway.lincutter.model.Link;

import java.util.List;

public interface LinkService {
    Link shortenLink(String originalUrl);

    String getLinkByShortUrl(String shortUrl);

    List<Link> getAllLinks();
}
