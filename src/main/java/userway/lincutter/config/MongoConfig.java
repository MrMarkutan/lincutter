package userway.lincutter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import userway.lincutter.model.Link;

@Configuration
public class MongoConfig {
    private final MongoTemplate mongoTemplate;

    public MongoConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        ensureIndexes();
    }

    private void ensureIndexes() {
        IndexOperations indexOps = mongoTemplate.indexOps(Link.class);
        indexOps.ensureIndex(new Index().on("shortUrl", Sort.Direction.ASC).unique());
    }
}
