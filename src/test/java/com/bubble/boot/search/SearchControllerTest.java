package com.bubble.boot.search;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bubble.boot.BubbleSpringMvc4Application;

/**
 * @author yanlin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		BubbleSpringMvc4Application.class,
		StubTwitterSearchConfig.class
})
@WebAppConfiguration
public class SearchControllerTest {

	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}
	
	@Test
	public void should_search() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/search/mixed;keywords=spring"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("resultPage"))
			.andExpect(MockMvcResultMatchers.model().attribute("tweets", 
					Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.model().attribute("tweets", 
					Matchers.hasItems(Matchers.hasProperty("text", Matchers.is("tweetText")),
							Matchers.hasProperty("text", Matchers.is("secondTweet")))));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
