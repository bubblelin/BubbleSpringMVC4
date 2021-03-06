package com.bubble.boot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;


/**
 * @author yanlin
 */
@Slf4j
@Controller
public class TweetController {

	@Autowired
	private Twitter twitter;
	
	
	@RequestMapping("/home")
	public String home() {
		return "searchPage";
	}
	
	@RequestMapping(value = "/postSearch", method=RequestMethod.POST)
	public String postSearch(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		
		String search = req.getParameter("search");
		if(search.toLowerCase().contains("struts")) {
			redirectAttributes.addFlashAttribute("error",  "Try using spring instead!");
			return "redirect:/";
		}
		redirectAttributes.addAttribute("search", search);

		//Redirect 会发送一个302头信息， 它会在浏览器内部触发导航，而Forward则不会导致URL的变化。
		return "redirect:result";
	}
	
	@RequestMapping("/result")
	public String hello(@RequestParam(defaultValue = "bubbleSpringMVC4") String search,
			Model model) {
		SearchResults searchResults = twitter.searchOperations().search(search);
		List<Tweet> tweets = searchResults.getTweets();
		
		model.addAttribute("tweets", tweets);
		model.addAttribute("search", search);
		log.debug("tweets.image: {}", tweets.get(0).getUser().getProfileImageUrl());
		return "resultPage";
	}
}
