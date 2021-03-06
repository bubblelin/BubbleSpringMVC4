package com.bubble.boot.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubble.boot.error.EntityNotFoundException;
import com.bubble.boot.user.User;
import com.bubble.boot.user.UserRepository;

/**
 * @author yanlin
 */
@RestController
@RequestMapping("/api")
public class UserApiController {

	private UserRepository userRepository;
	
	@Autowired
	public UserApiController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		HttpStatus status = HttpStatus.OK;
		if(userRepository.notExists(user.getEmail())) {
			status = HttpStatus.CREATED;
		}
		User saved = userRepository.save(user);
		return new ResponseEntity<User>(saved, status);
		
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) throws EntityNotFoundException {
		if(userRepository.notExists(user.getEmail())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userRepository.update(email, user), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable String email) throws EntityNotFoundException {
		if(userRepository.notExists(email)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepository.delete(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
