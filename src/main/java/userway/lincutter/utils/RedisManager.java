package userway.lincutter.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import userway.lincutter.model.Link;

@Component
public class RedisManager {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "shortened_link:";

    public RedisManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setLinkToRedis(Link link) {
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + link.getShortUrl(), link.getOriginalUrl());
    }

    public boolean isInRedis(String url) {
        return getFromRedis(url) != null;
    }

    public String getFromRedis(String url) {
        return redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + url);
    }
}
