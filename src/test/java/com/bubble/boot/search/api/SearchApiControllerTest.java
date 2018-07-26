package com.bubble.boot.search.api;
/**
 * @author yanlin
 */

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bubble.boot.BubbleSpringMvc4Application;
import com.bubble.boot.search.StubTwitterSearchConfig;
import com.bubble.boot.user.User;
import com.bubble.boot.user.UserRepository;
import com.bubble.boot.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		BubbleSpringMvc4Application.class,
		StubTwitterSearchConfig.class
})
@WebAppConfiguration
public class SearchApiControllerTest {

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
	public void should_search() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/api/search/mixed;keywords=spring")
					.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("yl_anin@126.com")));
	}
	
	@Test
	public void should_create_new_user() throws JsonProcessingException, Exception {
		User user = new User("yl.anin@126.com");
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/api/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtils.toJson(user)))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		Assertions.assertThat(userRepository.findAll())
			.extracting(User::getEmail)
			.containsOnly("yl_anin@126.com", "yl.anin@qq.com");
	}
	
	@Test
	public void should_delete_user() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/user/yl_anin@126.com")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Assertions.assertThat(userRepository.findAll()).hasSize(0);
	}
	
	@Test
	public void should_return_not_found_when_deleting_unknown_user() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/user/non-existing@mail.com")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void put_should_update_existing_user() throws JsonProcessingException, Exception {
		User user = new User("ignore@spring.io");
		this.mockMvc.perform(
				MockMvcRequestBuilders.put("/api/user/yl_anin@126.com")
					.content(JsonUtils.toJson(user))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Assertions.assertThat(userRepository.findAll())
			.extracting(User::getEmail)
			.containsOnly("yl_anin@126.com");

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
