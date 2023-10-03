package userway.lincutter.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import userway.lincutter.model.Link;

public interface LinkRepository extends MongoRepository<Link, String> {
    Link findByShortUrl(String shortUrl);

    Link findByOriginalUrl(String originalUrl);

    boolean existsByShortUrl(String shortUrl);
}
