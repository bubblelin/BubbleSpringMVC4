package com.bubble.boot.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.bubble.boot.search.cache.SearchCache;

/**
 * @author yanlin
 */
@Service
@Profile("!async")
public class SearchService implements TwitterSearch {

	private SearchCache searchCache;
	
	@Autowired
	public SearchService(Twitter twitter, SearchCache searchCache) {
		this.searchCache = searchCache;
	}
	
	/* (non-Javadoc)
	 * @see com.bubble.boot.search.TwitterSearch#search(java.lang.String, java.util.List)
	 */
	@Override
	public List<LightTweet> search(String searchType, List<String> keywords){
		return keywords.stream().
				flatMap(keyword -> searchCache.fetch(searchType, keyword).stream())
				.collect(Collectors.toList());
	}
	
}
