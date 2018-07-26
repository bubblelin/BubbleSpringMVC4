package com.bubble.boot.search;

import java.util.List;

/**
 * @author yanlin
 */
public interface TwitterSearch {

	List<LightTweet> search(String searchType, List<String> keywords);

}