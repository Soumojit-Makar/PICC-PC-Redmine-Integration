package com.nnp.redmineintegration.api.impl;

import com.nnp.redmineintegration.api.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nnp.redmineintegration.api.RedmineAPI;
import com.nnp.redmineintegration.api.exception.RedmineAPIException;
import com.nnp.redmineintegration.api.model.Issue;
import com.nnp.redmineintegration.api.model.Membership;
import com.nnp.redmineintegration.api.model.OnboardUser;
import com.nnp.redmineintegration.api.model.Project;
import com.nnp.redmineintegration.api.model.RootProject;
import com.nnp.redmineintegration.api.model.User;
import com.nnp.redmineintegration.api.model.response.getusers.UserList;
import com.nnp.redmineintegration.service.RedmineService;

import java.util.*;

@RestController
@Slf4j
public class RedmineAPIImpl implements RedmineAPI {
    @Autowired
    private RedmineService redmineService;

    @Override
    @PostMapping(path = "/createUser")
    public ResponseEntity<ResponseUser> createUser(User user, String apiKey) throws RedmineAPIException {
        return redmineService.createUser(user, apiKey);
    }

    @Override
    @GetMapping(path = "/getUsers")
    public Map<String, Integer> getUsers(@RequestParam String apiKey) throws RedmineAPIException {
        ResponseEntity<UserList> users = redmineService.getUsers(apiKey);
        Map<String, Integer> responseObject = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (users.getBody() != null) {
            users.getBody().getUsers().forEach(user -> responseObject.put(user.getLogin(), user.getId()));
        }
        return responseObject;
    }

    @Override
    @GetMapping(path = "/checkLoginExists")
    public Boolean checkIfLoginExists(@RequestParam String login, @RequestParam String apiKey) {
        boolean retVal = false;
        ResponseEntity<UserList> users = redmineService.getUsers(apiKey);
        if (users.getBody() != null && users.getBody().getUsers() != null) {
            for (com.nnp.redmineintegration.api.model.response.getusers.User user : users.getBody().getUsers()) {
                if (user.getLogin().equalsIgnoreCase(login)) {
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    @Override
    @GetMapping(path = "/getProjects")
    public List<com.nnp.redmineintegration.api.model.response.Project> getProjects(@RequestParam String apiKey) {
        List<com.nnp.redmineintegration.api.model.response.Project> projectList = new ArrayList<>();
        ResponseEntity<ResponseProjects> projects = redmineService.getProjects(apiKey);
        if (projects.getBody() != null && projects.getBody().projects != null) {
            projectList = projects.getBody().projects;
        }
        return projectList;
    }

    @Override
    @GetMapping(path = "/checkProjectExists")
    public Boolean checkIfProjectExists(@RequestParam String project, @RequestParam String apiKey) {
        boolean retVal = false;
        ResponseEntity<ResponseProjects> projects = redmineService.getProjects(apiKey);
        if (projects.getBody() != null && projects.getBody().projects != null) {
            List<com.nnp.redmineintegration.api.model.response.Project> projectList = projects.getBody().projects;
            for (com.nnp.redmineintegration.api.model.response.Project rmProject : projectList) {
                if (rmProject.getName().equalsIgnoreCase(project) || rmProject.getIdentifier().equalsIgnoreCase(project)) {
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    @Override
    @GetMapping(path = "/getAPIKey")
    public String getAPIKey(String userId, @RequestParam String apiKey) throws RedmineAPIException {
        return redmineService.getAPIKey(userId, apiKey);
    }

    @Override
    @DeleteMapping(path = "/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String id, @RequestParam String apiKey) {
        return redmineService.deleteUser(id, apiKey);
    }

    @Override
    @PostMapping(path = "/createProject")
    public ResponseEntity<ResponseProject> createProject(@RequestBody Project project, @RequestParam String apiKey) {
        return redmineService.createProject(project, apiKey);
    }

    @Override
    @DeleteMapping(path = "/deleteProject")
    public ResponseEntity<String> deleteProject(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "identifier", required = false) String identifier,
            @RequestParam(name = "apiKey") String apiKey) {
        String targetId = (id != null && !id.trim().isEmpty()) ? id : identifier;
        return redmineService.deleteProject(targetId, apiKey);
    }

    @Override
    @PostMapping(value = "/{projectId}/associateUser")
    public ResponseEntity<String> associateUserWithProject(Membership membership, @PathVariable String projectId, @RequestParam String apiKey) {
        return redmineService.associateUserWithProject(membership, projectId, apiKey);
    }

    @Override
    @GetMapping(value = "/getUserDetail")
    public ResponseEntity<String> getUserDetail(@RequestParam String id, @RequestParam String apiKey) {
        return redmineService.getUserDetails(id, apiKey);
    }

    @Override
    @PostMapping(value = "/onboarduser")
    public ResponseEntity<Boolean> onboardUser(@RequestBody OnboardUser onboardUser, @RequestParam String apiKey) {
        return redmineService.onboardUser(onboardUser, apiKey);
    }

    @Override
    @PostMapping(path = "/createIssue")
    public ResponseEntity<String> createIssue(@RequestBody Issue issue, @RequestParam String apiKey) throws RedmineAPIException {
        return redmineService.createIssue(issue, apiKey);
    }

    @Override
    @PostMapping(path = "/createSupportIssue")
    public ResponseEntity<String> createSupportIssue(@RequestBody Issue issue, @RequestParam String apiKey) throws RedmineAPIException {
        return redmineService.createSupportIssueForNewUser(issue, apiKey);  
    }

    @Override
    public ResponseEntity<String> getIssue(String projectId, String authorId, String trackerId, String apiKey) {
        return redmineService.getIssues(projectId, authorId, trackerId ,apiKey);
    }

    @Override
    @PutMapping(path = "/updateIssue")
    public ResponseEntity<String> updateIssue(@RequestBody Issue issue, @RequestParam String issueId, @RequestParam String apiKey) {
        return redmineService.updateIssue(issue, issueId, apiKey);
    }

    @Override
    @GetMapping(path = "/getIssuesDetails")
    public ResponseEntity<String> getIssueDetails(@RequestParam String id, @RequestParam String apiKey) {
        return redmineService.getIssueDetails(id, apiKey);
    }

    @Override
    @PostMapping(path = "/createRootProject")
    public ResponseEntity<RootProject> createRootProject(@RequestBody RootProject rootProject, @RequestParam String apiKey)
            throws RedmineAPIException {
        return redmineService.createRootProject(rootProject, apiKey);
    }
    
    @Override
    @GetMapping(path = "/getMembershipsForProj/{projId}")
    public ResponseEntity<List<ProjMembership>> getMembershipsForProj(@PathVariable String projId, @RequestParam String apiKey)
            throws RedmineAPIException {
    	List<ProjMembership> memberships = redmineService.getMembershipForProject(projId, apiKey).getBody().getMemberships();
        return new  ResponseEntity<>(memberships,HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity<IssueCategoriesResponse> getIssueCategories(String projectId, String apiKey) {
        return redmineService.getIssueCategories(projectId, apiKey);
    }

    @Override
    public ResponseEntity<TrackersResponse> getTrackers(String apiKey) {
        return redmineService.getTrackers(apiKey);
    }

    @Override
    public ResponseEntity<IssueStatusesResponse> getIssueStatuses(String apiKey) {
        return redmineService.getIssueStatuses(apiKey);
    }

    @Override
    public ResponseEntity<IssuePrioritiesResponse> getIssuePriorities(String apiKey) {
        return redmineService.getIssuePriorities(apiKey);
    }
}
