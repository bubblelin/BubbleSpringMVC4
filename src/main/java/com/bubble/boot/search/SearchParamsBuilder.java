package com.bubble.boot.search;

import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchParameters.ResultType;

/**
 * 创建缓存对象SearchParameters
 * @author yanlin
 */
public class SearchParamsBuilder {

	public static SearchParameters createSearchParam(String searchType, String taste) {
		SearchParameters.ResultType resultType = getResultType(searchType);
		SearchParameters searchParameters = new SearchParameters(taste);
		
		searchParameters.resultType(resultType);
		searchParameters.count(3);
		return searchParameters;
	}

	private static ResultType getResultType(String searchType) {
		for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {
			if(knownType.name().equalsIgnoreCase(searchType)) {
				return knownType;
			}
		}
		return SearchParameters.ResultType.RECENT;
	}
}
