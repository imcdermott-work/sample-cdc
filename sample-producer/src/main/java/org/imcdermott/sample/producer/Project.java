package org.imcdermott.sample.producer;

import java.util.List;

public class Project {
	
	private String name;
	private List<User> users;
	
	public Project() {
		
	}
	
	public Project(String name, List<User> users) {
		this.name = name;
		this.users = users;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
