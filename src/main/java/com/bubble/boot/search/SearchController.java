package com.bubble.boot.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yanlin
 */
@Controller
public class SearchController {

	private TwitterSearch searchService;
	
	@Autowired
	public SearchController(TwitterSearch searchService) {
		this.searchService = searchService;
	}
	
	@RequestMapping("/search/{searchType}")
	public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords) {
		List<LightTweet> tweets = searchService.search(searchType, keywords);
		ModelAndView mv = new ModelAndView("resultPage");
		mv.addObject("tweets", tweets);
		mv.addObject("search", String.join(",", keywords));
		return mv;
	}
}
