package com.bubble.boot.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

/**
 * @author yanlin
 */
@Configuration
@EnableCaching// 激活Spring的缓存
public class CacheConfiguration {

//	@Bean
//	public CacheManager cacheManager() {
//		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//		// 添加Web缓存, 缓存结果“searches”
//		simpleCacheManager.setCaches(Arrays.asList(
//				new ConcurrentMapCache("searches")));
//		return simpleCacheManager;
//	}
	
	
	/**
	 * 使用Guava的缓存管理
	 */
	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager("searches");
		
		//构建10分钟过期的缓存，并使用软引用，当JVM内存不足时会将缓存条目清除
		cacheManager.setCacheBuilder(CacheBuilder.newBuilder()
				.softValues()
				.expireAfterWrite(10, TimeUnit.MINUTES));
		return cacheManager;
	}
}
