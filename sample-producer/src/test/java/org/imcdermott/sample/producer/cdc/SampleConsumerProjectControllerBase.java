package org.imcdermott.sample.producer.cdc;


import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.imcdermott.sample.producer.CORSFilter;
import org.imcdermott.sample.producer.Project;
import org.imcdermott.sample.producer.ProjectController;
import org.imcdermott.sample.producer.ProjectService;
import org.imcdermott.sample.producer.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.minidev.json.JSONArray;

@RunWith(MockitoJUnitRunner.class)
/**
 * This is an example of a base class which uses a CorsFilter in the response from the mocked controller. 
 * CORSFilter class is essentially what you see here: https://memorynotfound.com/spring-mvc-restful-web-service-crud-example/
 */
public abstract class SampleConsumerProjectControllerBase {

	@Mock
	ProjectService projectService;
	
	@InjectMocks
	ProjectController projectController;
	
	@Before
	public void setup() throws JsonProcessingException{
		
		given(projectService.getProject(argThat(isAcceptableProjectId()))).willReturn(getDummyProjectForMockService());
		
		when(projectService.addProject(any(Project.class))).thenAnswer(getProjectNameFromInvocationArgument());
		
		
		MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(projectController).addFilters(new CORSFilter())
                .build();
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
	
	// extracted a method to put a descriptive name
	private static Answer<String> getProjectNameFromInvocationArgument() { 
	  return new Answer<String>() {
	    public String answer(InvocationOnMock invocation) {
	       return ((Project) invocation.getArguments()[0]).getName();
	    }
	  };
	}
	
	private TypeSafeMatcher<String> isAcceptableProjectId() {
		return new TypeSafeMatcher<String>() {
			@Override protected boolean matchesSafely(String projectId) {
				
				return projectId != null && projectId.matches("[a-zA-Z0-9]+");
			}

			@Override public void describeTo(Description description) {

			}
		};
	}
	

	private Project getDummyProjectForMockService() 
	{
		User dummyAdmin = new User("111", "dummyAdmin");
		User dummyManager = new User("111", "dummyManager");
		List<User> users = new ArrayList<User>();
		users.add(dummyAdmin);
		users.add(dummyManager);
		Project dummyProject = new Project("DummyProject", users);
		return dummyProject;
	}
	
	public void assertThatListContainsValidUsers(JSONArray array) throws JsonParseException, JsonMappingException, IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		List<User> users = Arrays.asList(mapper.readValue(array.toString(), User[].class));
		for(User user: users) 
		{
			Assert.assertNotNull(user.getId());
			Assert.assertNotNull(user.getName());
		}
		
	}
	
}