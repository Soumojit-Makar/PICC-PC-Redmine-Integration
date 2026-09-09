package com.nnp.redmineintegration.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nnp.redmineintegration.api.model.Issue;
import com.nnp.redmineintegration.api.model.Membership;
import com.nnp.redmineintegration.api.model.Project;
import com.nnp.redmineintegration.api.model.User;
import com.nnp.redmineintegration.api.model.response.ResponseProject;
import com.nnp.redmineintegration.api.model.response.ResponseProjects;
import com.nnp.redmineintegration.api.model.response.ResponseUser;
import com.nnp.redmineintegration.api.model.response.getusers.UserList;


@FeignClient(name = "${feign.support.name}", url = "${feign.support.url}")
public interface RedmineSupportClient {
    @PostMapping(value = "/users.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseUser> createUser(@RequestBody User user, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/users.json?limit=100", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserList> getUsers(@RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @DeleteMapping(value = "/users/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteUser(@PathVariable Integer id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @PostMapping(value = "/projects.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseProject> createProject(@RequestBody Project project, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @DeleteMapping(value = "/projects/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteProject(@PathVariable String id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);


    @PostMapping(value = "/projects/{projectId}/memberships.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> associateUserWithProject(@RequestBody Membership membership, @PathVariable String projectId, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/users/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getUserDetails(@PathVariable Integer id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/users/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getAPIKey(@PathVariable Integer id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/projects.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseProjects> getProjects(@RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @PostMapping(value = "/issues.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createIssues(@RequestBody Issue issue, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);


//    @GetMapping(value = "/issues.json?author_id={issueStatus}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/issues.json?project_id={projectId}&author_id={authorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getIssues(@PathVariable String projectId, @PathVariable String authorId, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);


    @GetMapping(value = "/issues/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getIssueDetail(@PathVariable String id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @PutMapping(value = "/issues/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateIssue(@RequestBody Issue issue, @PathVariable String id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);
}