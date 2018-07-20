package com.bubble.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bubble.boot.service.SearchService;

/**
 * @author yanlin
 */
@Controller
public class SearchController {

	private SearchService searchService;
	
	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	@RequestMapping("/search/{searchType}")
	public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
		List<Tweet> tweets = searchService.search(searchType, keywords);
		ModelAndView mv = new ModelAndView("resultPage");
		mv.addObject("tweets", tweets);
		mv.addObject("search", String.join(",", keywords));
		return mv;
	}
}
