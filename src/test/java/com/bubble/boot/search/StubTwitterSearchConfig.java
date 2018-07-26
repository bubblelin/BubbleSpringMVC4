package com.bubble.boot.search;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author yanlin
 */
@Configuration
public class StubTwitterSearchConfig {

	@Bean
	@Primary
	public TwitterSearch twitterSearch() {
		return (searchType, keywords) -> Arrays.asList(
					new LightTweet("tweetText"),
					new LightTweet("secondTweet")
				);
	}
}
