package com.bubble.boot.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.bubble.boot.search.cache.SearchCache;

/**
 * @author yanlin
 */
@Service
@Profile("async")
public class ParallelSearchService implements TwitterSearch {

	private final AsyncSearch asyncSearch;
	
	@Autowired
	public ParallelSearchService(AsyncSearch asyncSearch) {
		this.asyncSearch = asyncSearch;
	}
	
	@Override
	public List<LightTweet> search(String searchType, List<String> keywords) {
		CountDownLatch latch = new CountDownLatch(keywords.size());
		List<LightTweet> allTweets = Collections.synchronizedList(new ArrayList<>());
		keywords.stream().forEach(keyword -> 
			asyncFetch(latch, allTweets, searchType, keyword));
		
		await(latch);
		return allTweets;
	}

	private void await(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	private void asyncFetch(CountDownLatch latch, List<LightTweet> allTweets, 
			String searchType, String keyword) {
		asyncSearch.asyncFetch(searchType, keyword)
			.addCallback(tweets -> onSuccess(allTweets, latch, tweets), 
					ex -> onError(latch, ex));
	}

	
	private void onError(CountDownLatch latch, Throwable ex) {
		ex.printStackTrace();
		latch.countDown();
	}

	private void onSuccess(List<LightTweet> allTweets, CountDownLatch latch, 
			List<LightTweet> tweets) {
		allTweets.addAll(tweets);
		latch.countDown();
	}


	private static class AsyncSearch{
		
		protected final Log log = LogFactory.getLog(getClass()); 
		private SearchCache searchCache;
		
		@Autowired
		public AsyncSearch(SearchCache searchCache) {
			this.searchCache = searchCache;
		}
		
		public ListenableFuture<List<LightTweet>> asyncFetch(String searchType, String keyword){
			log.info(Thread.currentThread().getName() + " - 搜索 " + keyword);
			return new AsyncResult<>(searchCache.fetch(searchType, keyword));
		}
		
		
	}
}
