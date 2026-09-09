package com.nnp.redmineintegration.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.nnp.redmineintegration.api.model.response.*;
import com.nnp.redmineintegration.utils.LogUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nnp.redmineintegration.api.client.RedmineClient;
import com.nnp.redmineintegration.api.client.RedmineSupportClient;
import com.nnp.redmineintegration.api.exception.RedmineAPIException;
import com.nnp.redmineintegration.api.model.Issue;
import com.nnp.redmineintegration.api.model.IssueRequest;
import com.nnp.redmineintegration.api.model.Membership;
import com.nnp.redmineintegration.api.model.MembershipRequest;
import com.nnp.redmineintegration.api.model.OnboardUser;
import com.nnp.redmineintegration.api.model.Project;
import com.nnp.redmineintegration.api.model.ProjectRequest;
import com.nnp.redmineintegration.api.model.RootProject;
import com.nnp.redmineintegration.api.model.User;
import com.nnp.redmineintegration.api.model.UserRequest;
import com.nnp.redmineintegration.api.model.response.getusers.UserList;
import com.nnp.redmineintegration.service.RedmineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedmineServiceImpl implements RedmineService {

    private final RedmineClient redmineClient;
    private final RedmineSupportClient redmineSupportClient;

    @Value("${redmine.role}")
    private Integer userRole;

    @Override
    public ResponseEntity<UserList> getUsers(String apiKey) throws RedmineAPIException {
        log.info("Fetching users list from Redmine");
        try {
            return redmineClient.getUsers(apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching users list from Redmine: {}", LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(String id, String apiKey) {
        try {
            return redmineClient.deleteUser(Integer.parseInt(id), apiKey);
        } catch (Exception e) {
            log.error("Error occurred while deleting user with id: {} in Redmine: {} {}",
                    LogUtils.sanitizeForLog(id), LogUtils.sanitizeForLog(e.getMessage()), LogUtils.sanitizeForLog(e.toString()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<ResponseProject> createProject(Project project, String apiKey) {
        ProjectRequest projectRequest = project.getProject();
        String toLowerCase = projectRequest.getIdentifier().toLowerCase();
        String newIdentifier = toLowerCase.replaceAll("[~!@#$%^&*(){},.|:;']", "");
        projectRequest.setIdentifier(newIdentifier);
        try {
            return redmineClient.createProject(project, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while creating project in Redmine with identifier: {}: {}",
                    LogUtils.sanitizeForLog(newIdentifier), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> deleteProject(String id, String apiKey) {
        log.info("Deleting project with id: {} in Redmine", LogUtils.sanitizeForLog(id));
        try {
            return redmineClient.deleteProject(id, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while deleting project with id: {} in Redmine: {}",
                    LogUtils.sanitizeForLog(id), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> associateUserWithProject(Membership membership, String projectId, String apiKey) {
        Integer userId = (membership != null && membership.getMembership() != null)
                ? membership.getMembership().getUserId() : null;
        try {
            return redmineClient.associateUserWithProject(membership, projectId, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while associating user: {} with project: {}: {}",
                    LogUtils.sanitizeForLog(String.valueOf(userId)),
                    LogUtils.sanitizeForLog(projectId),
                    LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> getUserDetails(String id, String apiKey) {
        try {
            return redmineClient.getUserDetails(Integer.parseInt(id), apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching details for user ID: {} from Redmine: {}",
                    LogUtils.sanitizeForLog(id), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<Boolean> onboardUser(OnboardUser onboardUser, String apiKey) {
        String userType = onboardUser.getUserType();

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname(onboardUser.getFirstname());
        userRequest.setLastname(onboardUser.getLastname());
        userRequest.setLogin(onboardUser.getLogin());
        userRequest.setMail(onboardUser.getMail());
        userRequest.setPassword(onboardUser.getPassword());

        User user = new User();
        user.setUser(userRequest);

        ResponseEntity<ResponseUser> createdUser = createUser(user, apiKey);
        if (createdUser.getStatusCode().equals(HttpStatus.CREATED)) {

            ProjectRequest projectRequest = new ProjectRequest();
            projectRequest.setName(onboardUser.getProjectName());
            projectRequest.setIdentifier(onboardUser.getIdentifier());

            Project project = new Project();
            project.setProject(projectRequest);

            boolean existingProject = false;
            int existingProjectId = 0;
            ResponseEntity<ResponseProjects> projects = redmineClient.getProjects(apiKey);
            if (projects.getBody() != null && projects.getBody().projects != null) {
                List<com.nnp.redmineintegration.api.model.response.Project> projectList = projects.getBody().projects;
                for (com.nnp.redmineintegration.api.model.response.Project proj : projectList) {
                    if (proj.getName().equalsIgnoreCase(onboardUser.getProjectName())
                            && proj.getIdentifier().equalsIgnoreCase(onboardUser.getIdentifier())) {
                        existingProject = true;
                        existingProjectId = proj.getId();
                        break;
                    }
                }
            }

            if (existingProject) {
                MembershipRequest membershipRequest = new MembershipRequest();
                membershipRequest.setUserId(Objects.requireNonNull(createdUser.getBody()).getUser().getId());
                membershipRequest.setRoleIds(Collections.singletonList(userRole));

                Membership membership = new Membership();
                membership.setMembership(membershipRequest);

                ResponseEntity<String> createdAssociation = associateUserWithProject(
                        membership, String.valueOf(existingProjectId), apiKey);
                if (createdAssociation.getStatusCode().equals(HttpStatus.CREATED)) {
                    return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
                } else {
                    log.error("project and user association failed --> project {} user --> {}",
                            LogUtils.sanitizeForLog(String.valueOf(existingProjectId)),
                            LogUtils.sanitizeForLog(onboardUser.getLogin()));
                    return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                if ("superAdmin".equals(userType)) {
                    ResponseEntity<ResponseProject> createdProject = createProject(project, apiKey);

                    if (createdProject.getStatusCode().equals(HttpStatus.CREATED)) {
                        MembershipRequest membershipRequest = new MembershipRequest();
                        membershipRequest.setUserId(Objects.requireNonNull(createdUser.getBody()).getUser().getId());
                        membershipRequest.setRoleIds(Collections.singletonList(userRole));

                        Membership membership = new Membership();
                        membership.setMembership(membershipRequest);

                        ResponseEntity<String> createdAssociation = associateUserWithProject(
                                membership,
                                String.valueOf(Objects.requireNonNull(createdProject.getBody()).getProject().getId()),
                                apiKey);
                        if (createdAssociation.getStatusCode().equals(HttpStatus.CREATED)) {
                            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
                        } else {
                            log.error("project and user association failed --> project {} user --> {}",
                                    LogUtils.sanitizeForLog(onboardUser.getProjectName()),
                                    LogUtils.sanitizeForLog(onboardUser.getLogin()));
                            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        log.error("project creation failed --> project {}", LogUtils.sanitizeForLog(onboardUser.getProjectName()));
                        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    log.error("project does not exists --> project {}", LogUtils.sanitizeForLog(onboardUser.getProjectName()));
                    log.error("cannot create new project as user type not superAdmin");
                    return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.error("error in user creation --> user {}", LogUtils.sanitizeForLog(onboardUser.getLogin()));
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseUser> createUser(User user, String apiKey) throws RedmineAPIException {
        String login = (user != null && user.getUser() != null) ? user.getUser().getLogin() : "unknown";
        try {
            return redmineClient.createUser(user, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while creating user: {} in Redmine: {}",
                    LogUtils.sanitizeForLog(login), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> createIssue(Issue issue, String apiKey) {
        String subject = (issue != null && issue.getIssue() != null) ? issue.getIssue().getSubject() : "unknown";
        try {
            return redmineClient.createIssues(issue, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while creating issue: '{}' in Redmine: {}",
                    LogUtils.sanitizeForLog(subject), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> createSupportIssueForNewUser(Issue issue, String apiKey) {
        String subject = (issue != null && issue.getIssue() != null) ? issue.getIssue().getSubject() : "unknown";
        try {
            return redmineSupportClient.createIssues(issue, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while creating support issue for new user: '{}' in Redmine: {}",
                    LogUtils.sanitizeForLog(subject), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> getIssues(String projectId, String authorId, String trackerId, String apiKey) {
        try {
            return redmineClient.getIssues(projectId, authorId, trackerId, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching issues for project: {} from Redmine: {}",
                    LogUtils.sanitizeForLog(projectId), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> getIssueDetails(String id, String apiKey) {
        try {
            return redmineClient.getIssueDetail(id, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching issue details for ID: {} from Redmine: {}",
                    LogUtils.sanitizeForLog(id), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> updateIssue(Issue issue, String issueId, String apiKey) {
        try {
            return redmineClient.updateIssue(issue, issueId, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while updating issue ID: {} in Redmine: {}",
                    LogUtils.sanitizeForLog(issueId), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public String getAPIKey(String userId, String apiKey) {
        try {
            ResponseEntity<User> userResponseEntity = redmineClient.getAPIKey(Integer.valueOf(userId), apiKey);
            // response.User has field apiKey mapped from JSON "api_key" — Lombok generates getApiKey()
            return Objects.requireNonNull(Objects.requireNonNull(userResponseEntity).getBody()).getUser().getApiKey();
        } catch (Exception e) {
            log.error("Error occurred while fetching API key for user ID: {} in Redmine: {}",
                    LogUtils.sanitizeForLog(userId), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<RootProject> createRootProject(RootProject rootProject, String apiKey) {

        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName(rootProject.getName());
        projectRequest.setIdentifier(rootProject.getIdentifier());
        projectRequest.setDescription(rootProject.getDescription());
        projectRequest.setInheritMembers(false);
        projectRequest.setIsPublic(false);
        projectRequest.setEnabledModuleNames(Arrays.asList(
                "boards", "calendar", "documents", "files", "gantt",
                "issue_tracking", "news", "repository", "time_tracking", "wiki"));

        HashMap<String, String> custFieldValueMap = new HashMap<>();
        custFieldValueMap.put("2", rootProject.getLogin());
        projectRequest.setCustomFieldValMap(custFieldValueMap);

        Project project = new Project();
        project.setProject(projectRequest);

        ResponseEntity<ResponseProject> projectRes = redmineClient.createProject(project, apiKey);
        if (projectRes.getStatusCode() == HttpStatus.CREATED) {
            String projectId = String.valueOf(Objects.requireNonNull(projectRes.getBody()).getProject().getId());
            rootProject.setProjectId(projectId);

            UserRequest userRequest = new UserRequest();
            userRequest.setLogin(rootProject.getLogin());
            userRequest.setFirstname(rootProject.getFirstname());
            userRequest.setLastname(rootProject.getLastname());
            userRequest.setMail(rootProject.getMail());
            userRequest.setPassword(rootProject.getPassword());

            User user = new User();
            user.setUser(userRequest);

            ResponseEntity<ResponseUser> userRes = redmineClient.createUser(user, apiKey);

            if (userRes.getStatusCode() == HttpStatus.CREATED) {
                int userId = Objects.requireNonNull(userRes.getBody()).getUser().getId();
                rootProject.setUserId(String.valueOf(userId));

                MembershipRequest membershipRequest = new MembershipRequest();
                membershipRequest.setUserId(Integer.parseInt(rootProject.getUserId()));
                membershipRequest.setRoleIds(List.of(3)); // 3 = Manager

                Membership membership = new Membership();
                membership.setMembership(membershipRequest);

                ResponseEntity<String> associateUserWithProject =
                        redmineClient.associateUserWithProject(membership, rootProject.getProjectId(), apiKey);

                if (associateUserWithProject.getStatusCode() == HttpStatus.CREATED) {
                    return new ResponseEntity<>(rootProject, HttpStatus.OK);
                } else {
                    log.error("Error occurred while associating user {} with project {}",
                            LogUtils.sanitizeForLog(String.valueOf(userId)),
                            LogUtils.sanitizeForLog(projectId));
                    String responseBody = associateUserWithProject.getBody();
                    log.error("Error Code {} Error Message {}",
                            LogUtils.sanitizeForLog(associateUserWithProject.getStatusCode().toString()),
                            LogUtils.sanitizeForLog(responseBody));
                    RedmineAPIException rmex = new RedmineAPIException();
                    rmex.setMessage(responseBody);
                    rmex.setStatus(associateUserWithProject.getStatusCode().value());
                    throw rmex;
                }
            } else {
                log.error("Error occurred while creating user");
                log.error("Error Code {} Error Message {}",
                        LogUtils.sanitizeForLog(String.valueOf(userRes.getStatusCode())),
                        LogUtils.sanitizeForLog(String.valueOf(userRes.getBody())));
                RedmineAPIException rmex = new RedmineAPIException();
                rmex.setMessage(String.valueOf(userRes.getBody()));
                rmex.setStatus(userRes.getStatusCode().value());
                throw rmex;
            }
        } else {
            log.error("Error occurred while creating project");
            log.error("Error Code {} Error Message {}",
                    LogUtils.sanitizeForLog(String.valueOf(projectRes.getStatusCode())),
                    LogUtils.sanitizeForLog(String.valueOf(projectRes.getBody())));
            RedmineAPIException rmex = new RedmineAPIException();
            rmex.setMessage(String.valueOf(projectRes.getBody()));
            rmex.setStatus(projectRes.getStatusCode().value());
            throw rmex;
        }
    }

    @Override
    public ResponseEntity<ResponseProjects> getProjects(String apiKey) {
        try {
            return redmineClient.getProjects(apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching projects list from Redmine: {}",
                    LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<ResponseMemberships> getMembershipForProject(String projId, String apiKey) {
        try {
            return redmineClient.getMembershipsForProject(projId, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching membership for project ID: {} from Redmine: {}",
                    LogUtils.sanitizeForLog(projId), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<IssueCategoriesResponse> getIssueCategories(String projectId, String apiKey) {
        try {
            return redmineClient.getIssueCategories(projectId, apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching issue categories for project ID: {} from Redmine: {}",
                    LogUtils.sanitizeForLog(projectId), LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<TrackersResponse> getTrackers(String apiKey) {
        try {
            return redmineClient.getTrackers(apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching trackers from Redmine: {}",
                    LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<IssueStatusesResponse> getIssueStatuses(String apiKey) {
        try {
            return redmineClient.getIssueStatuses(apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching issue statuses from Redmine: {}",
                    LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }

    @Override
    public ResponseEntity<IssuePrioritiesResponse> getIssuePriorities(String apiKey) {
        try {
            return redmineClient.getIssuePriorities(apiKey);
        } catch (Exception e) {
            log.error("Error occurred while fetching issue priorities from Redmine: {}",
                    LogUtils.sanitizeForLog(e.getMessage()));
            throw e;
        }
    }
}