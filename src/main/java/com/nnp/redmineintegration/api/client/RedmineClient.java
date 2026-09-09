package com.nnp.redmineintegration.api.client;

import com.nnp.redmineintegration.api.model.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nnp.redmineintegration.api.model.Issue;
import com.nnp.redmineintegration.api.model.Membership;
import com.nnp.redmineintegration.api.model.Project;
import com.nnp.redmineintegration.api.model.User;
import com.nnp.redmineintegration.api.model.response.getusers.UserList;


@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface RedmineClient {
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

    /**
     * @param membership - role_ids (1, 'Non member')
     * @param membership - role_ids (2, 'Anonymous')
     * @param membership - role_ids (3, 'Manager')
     * @param membership - role_ids (4, 'Developer')
     * @param membership - role_ids (5, 'Reporter')
     * @param membership - role_ids (6, 'DevOps-Architect')
     * @param membership - role_ids (7, 'Tech Lead')
     * @param membership - role_ids (8, 'DevOps-Engineer')
     * @param membership - user_id
     * @param projectId
     * @return
     */
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

    /**
     * @param issue_id:       get issue with the given id or multiple issues by id using ',' to separate id.
     * @param author_id:      get issue with created by the author.
     * @param project_id:     get issues from the project with the given id (a numeric value, not a project identifier).
     * @param subproject_id:  get issues from the subproject with the given id. You can use project_id=XXX&subproject_id=!* to get only the issues of a given project and none of its subprojects.
     * @param tracker_id:     get issues from the tracker with the given id
     * @param status_id:      get issues with the given status id only. Possible values: open, closed, * to get open and closed issues, status id
     * @param assigned_to_id: get issues which are assigned to the given user id. me can be used instead an ID to fetch all issues from the logged in user (via API key or HTTP auth)
     * @param parent_id:      get issues whose parent issue is given id.
     * @param cf_x:           get issues with the given value for custom field with an ID of x. (Custom field must have 'used as a filter' checked.)
     * @return Array of Issue as ResponseEntity.
     * @Sample GET /issues.xml
     * @Sample GET /issues.xml?issue_id=1
     * @Sample GET /issues.xml?issue_id=1,2
     * @Sample GET /issues.xml?project_id=2
     * @Sample GET /issues.xml?project_id=2&tracker_id=1
     * @Sample GET /issues.xml?assigned_to_id=6
     * @Sample GET /issues.xml?assigned_to_id=me
     * @Sample GET /issues.xml?status_id=closed
     * @Sample GET /issues.xml?status_id=*
     * @Sample GET /issues.xml?cf_1=abcdef
     * @Sample GET /issues.xml?sort=category:desc,updated_on
     */
//    @GetMapping(value = "/issues.json?author_id={issueStatus}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/issues.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getIssues(
            @RequestParam("project_id") String projectId,
            @RequestParam("author_id") String authorId,
            @RequestParam("tracker_id") String trackerId,
            @RequestHeader(name = "X-Redmine-API-Key") String apiKey
    );

    /**
     * @return
     * @Sample GET /issues/2.xml
     * @Sample GET /issues/2.json
     * @Sample GET /issues/2.xml
     * @Sample GET /issues/2.xml?include=attachments
     * @Sample GET /issues/2.xml?include=attachments,journals
     */
    @GetMapping(value = "/issues/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getIssueDetail(@PathVariable String id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @PutMapping(value = "/issues/{id}.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateIssue(@RequestBody Issue issue, @PathVariable String id, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);
    
    @GetMapping(value = "/projects/{projectId}/memberships.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMemberships> getMembershipsForProject(@PathVariable String projectId,@RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/projects/{projectId}/issue_categories.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IssueCategoriesResponse> getIssueCategories(@PathVariable String projectId, @RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/trackers.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TrackersResponse> getTrackers(@RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/issue_statuses.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IssueStatusesResponse> getIssueStatuses(@RequestHeader(name = "X-Redmine-API-Key") String apiKey);

    @GetMapping(value = "/enumerations/issue_priorities.json", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<IssuePrioritiesResponse> getIssuePriorities(@RequestHeader(name = "X-Redmine-API-Key") String apiKey);
}