package org.imcdermott.sample.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;


/*
 * This controller is used for example purposes.
 */
@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public Project getProject(@PathVariable(value="projectId") String projectId) {
    
    return projectService.getProject(projectId);
    	
    }
    
    @RequestMapping(value = "/project/add", consumes = "application/json", method = RequestMethod.POST)
    public String addProject(@RequestBody Project project) throws JsonProcessingException {
		
    	return projectService.addProject(project);
	}
    
}
