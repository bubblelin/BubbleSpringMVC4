package com.bubble.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bubble.boot.profile.UserProfileSession;

@Controller
public class HomeController {

	private final UserProfileSession userProfileSession;
	
	@Autowired
	public HomeController(UserProfileSession userProfileSession){
		this.userProfileSession = userProfileSession;
	}
	
	
	@RequestMapping("/")
	public String home(){
		List<String> tastes = userProfileSession.getTastes();
		if(tastes.isEmpty()){
			return "redirect:/profile";
		}
		return "redirect:/search/mixed;keywords=" + String.join(",", tastes);
	}
}
