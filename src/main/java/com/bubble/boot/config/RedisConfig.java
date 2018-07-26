package com.bubble.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author yanlin
 */
@Configuration
@Profile("redis")
@EnableRedisHttpSession
public class RedisConfig {

	
}
