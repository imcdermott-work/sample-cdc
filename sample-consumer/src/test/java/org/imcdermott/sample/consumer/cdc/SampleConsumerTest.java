package org.imcdermott.sample.consumer.cdc;

import java.util.ArrayList;
import java.util.List;

import org.imcdermott.sample.consumer.Project;
import org.imcdermott.sample.consumer.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.junit.Assert;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = {"org.imcdermott.sample:sample-producer:+:stubs:8080"}, workOffline = true)
public class SampleConsumerTest {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void testAddProject() {
		
		User admin = new User("1", "Admin");
		User regularUser = new User("1", "Regular User");
		List<User> users = new ArrayList<User>();
		users.add(admin);
		users.add(regularUser);
		
		Project project = new Project("SampleProject", users);
		
		String response = restTemplate.postForObject("http://localhost:8080/project/add", project, String.class);
		Assert.assertEquals(project.getName(), response);
		
	}
	
	@Test
	public void testGetProject() {
		Project project = restTemplate.getForObject("http://localhost:8080/project/12", Project.class);
		System.out.println("Project Name: " + project.getName());
		System.out.println("User details:");
		for(User user: project.getUsers()) 
		{
			System.out.println("Name: " + user.getName());
			System.out.println("Id: " + user.getId());
		}
	}

}
