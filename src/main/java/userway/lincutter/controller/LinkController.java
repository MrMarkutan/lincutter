package userway.lincutter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userway.lincutter.model.Link;
import userway.lincutter.service.LinkService;

import java.util.List;


@RestController
@RequestMapping("/api/links")
public class LinkController {
    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenLink(@RequestBody String originalUrl) {
        return new ResponseEntity<>(linkService.shortenLink(originalUrl).getShortUrl(),
                HttpStatus.CREATED);
    }

    @PostMapping("/resolve")
    public ResponseEntity<String> resolveLink(@RequestBody String shortUrl) {
        String linkByShortUrl = linkService.getLinkByShortUrl(shortUrl);
        if (linkByShortUrl != null) {
            return new ResponseEntity<>(linkByShortUrl, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Shortened URL not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Link>> listAllLinks() {
        return new ResponseEntity<>(linkService.getAllLinks(), HttpStatus.OK);
    }
}
