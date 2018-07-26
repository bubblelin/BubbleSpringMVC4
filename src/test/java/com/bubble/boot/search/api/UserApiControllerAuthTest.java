package com.bubble.boot.search.api;
/**
 * @author yanlin
 */

import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bubble.boot.user.User;
import com.bubble.boot.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserApiControllerAuthTest {

	@Autowired
	private FilterChainProxy springSecurityFilter;
	
	@Autowired
	private WebApplicationContext ctx;
	
	@Autowired
	private UserRepository userRepository;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
		userRepository.reset(new User("yl_anin@126.com"));
	}
	
	@Test
	public void unauthenticated_cannot_list_users() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/api/users")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void admin_can_list_users() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/api/users")
					.accept(MediaType.APPLICATION_JSON)
					.header("Authorization", basicAuth("admin", "admin")));
	}
	
	
	private String basicAuth(String login, String password) {
		byte[] auth = (login + ":" + password).getBytes();
		return "Basic " + Base64.getEncoder().encodeToString(auth);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
