package com.nnp.redmineintegration.service;

import com.nnp.redmineintegration.api.model.response.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.nnp.redmineintegration.api.exception.RedmineAPIException;
import com.nnp.redmineintegration.api.model.Issue;
import com.nnp.redmineintegration.api.model.Membership;
import com.nnp.redmineintegration.api.model.OnboardUser;
import com.nnp.redmineintegration.api.model.Project;
import com.nnp.redmineintegration.api.model.RootProject;
import com.nnp.redmineintegration.api.model.User;
import com.nnp.redmineintegration.api.model.response.getusers.UserList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

public interface RedmineService {
    ResponseEntity<UserList> getUsers(String apiKey) throws RedmineAPIException;

    ResponseEntity<String> deleteUser(String id, String apiKey);

    ResponseEntity<ResponseProject> createProject(Project project, String apiKey);

    ResponseEntity<String> deleteProject(String id, String apiKey);

    ResponseEntity<String> associateUserWithProject(Membership membership, String projectId, String apiKey);

    ResponseEntity<String> getUserDetails(String id, String apiKey);

    ResponseEntity<Boolean> onboardUser(OnboardUser onboardUser, String apiKey);

    ResponseEntity<ResponseUser> createUser(User user, String apiKey) throws RedmineAPIException;

    ResponseEntity<String> createIssue(Issue issue, String apiKey);

    ResponseEntity<String> createSupportIssueForNewUser(Issue issue, String apiKey);

    ResponseEntity<String> getIssues(String projectId, String authorId, String trackerId, String apiKey);

    ResponseEntity<String> getIssueDetails(String id, String apiKey);

    ResponseEntity<String> updateIssue(Issue issue, String issueId, String apiKey);

    String getAPIKey(String userId, String apiKey);
    
    ResponseEntity<RootProject> createRootProject(RootProject rootProject, String apiKey);

    ResponseEntity<ResponseProjects> getProjects(String apiKey);

	ResponseEntity<ResponseMemberships> getMembershipForProject(String projId, String apiKey);

    ResponseEntity<IssueCategoriesResponse> getIssueCategories(String projectId, String apiKey);

    ResponseEntity<TrackersResponse> getTrackers(String apiKey);

    ResponseEntity<IssueStatusesResponse> getIssueStatuses(String apiKey);

    ResponseEntity<IssuePrioritiesResponse> getIssuePriorities(String apiKey);
}
