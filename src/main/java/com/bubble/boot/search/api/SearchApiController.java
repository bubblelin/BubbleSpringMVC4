package com.bubble.boot.search.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubble.boot.error.EntityNotFoundException;
import com.bubble.boot.search.LightTweet;
import com.bubble.boot.search.TwitterSearch;
import com.bubble.boot.user.User;
import com.bubble.boot.user.UserRepository;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {

	private UserRepository userRepository;
	private TwitterSearch searchService;
	
	@Autowired
	public SearchApiController(TwitterSearch searchService, UserRepository userRepository){
		this.searchService = searchService;
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
	public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
		return searchService.search(searchType, keywords);
	}
	
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User createUser(@RequestBody User user){
		return userRepository.save(user);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String email, @RequestBody User user) throws EntityNotFoundException{
		return userRepository.update(email, user);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String email) throws EntityNotFoundException{
		userRepository.delete(email);
	}
}
