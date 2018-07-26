package com.bubble.boot.config;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanlin
 */
@Configuration
@EnableCaching// 激活Spring的缓存
public class CacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		// 添加Web缓存, 缓存结果“searches”
		simpleCacheManager.setCaches(Arrays.asList(
				new ConcurrentMapCache("searches")));
		return simpleCacheManager;
	}
}
