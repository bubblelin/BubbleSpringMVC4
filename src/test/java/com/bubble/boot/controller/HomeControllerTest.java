package com.bubble.boot.controller;
/**
 * @author yanlin
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bubble.boot.BubbleSpringMvc4Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubbleSpringMvc4Application.class)
@WebAppConfiguration
public class HomeControllerTest {

	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		// 使用Web应用上下文搭建MockMvc
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}
	
	@Test
	public void should_redirect_to_profile() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/profile"));
	}
	
	/**
	 * put this test below the other one
	 * @throws Exception
	 */
	@Test
	public void should_redirect_to_tastes() throws Exception {
		
		MockHttpSession session = new SessionBuilder().userTastes("spring", "groovy").build();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/").session(session))
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.redirectedUrl(
					"/search/mixed;keywords=spring.groovy"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
