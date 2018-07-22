package com.bubble.boot.search.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubble.boot.search.LightTweet;
import com.bubble.boot.search.SearchService;
import com.bubble.boot.user.User;
import com.bubble.boot.user.UserRepository;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {

	private UserRepository userRepository;
	private SearchService searchService;
	
	@Autowired
	public SearchApiController(SearchService searchService){
		this.searchService = searchService;
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/{searchType}", method = RequestMethod.POST)
	public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
		return searchService.search(searchType, keywords);
	}
	
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User createUser(@RequestBody User user){
		return userRepository.Save(user);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String email, @RequestBody User user){
		return userRepository.save(email, user);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String email){
		userRepository.delete(email);
	}
}
