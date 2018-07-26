package com.bubble.boot.search;
/**
 * @author yanlin
 */

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bubble.boot.BubbleSpringMvc4Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BubbleSpringMvc4Application.class)
public class SearchControllerMockTest {

	@Mock
	private SearchService searchService;
	
	@InjectMocks
	private SearchController searchController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		// 不使用Web应用上下文搭建MockMvc, 而是创建一个独立的上下文
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(searchController)
				.setRemoveSemicolonContent(false)
				.build();
	}
	
	@Test
	public void should_search() throws Exception {
		Mockito.when(searchService.search(Mockito.anyString(), Mockito.anyListOf(String.class)))
			.thenReturn(Arrays.asList(new LightTweet("tweetText")));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/search/mixed;keywords=spring"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("resultPage"))
			.andExpect(MockMvcResultMatchers.model().attribute("tweets", 
					Matchers.everyItem(Matchers.hasProperty("text", Matchers.is("tweetText")))));
		
		Mockito.verify(searchService, Mockito.times(1)).search(Mockito.anyString(), 
				Mockito.anyListOf(String.class));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
