package com.nnp.redmineintegration.api;

import com.nnp.redmineintegration.api.exception.RedmineAPIException;
import com.nnp.redmineintegration.api.model.Issue;
import com.nnp.redmineintegration.api.model.Membership;
import com.nnp.redmineintegration.api.model.OnboardUser;
import com.nnp.redmineintegration.api.model.Project;
import com.nnp.redmineintegration.api.model.RootProject;
import com.nnp.redmineintegration.api.model.User;
import com.nnp.redmineintegration.api.model.response.IssueCategoriesResponse;
import com.nnp.redmineintegration.api.model.response.IssuePrioritiesResponse;
import com.nnp.redmineintegration.api.model.response.IssueStatusesResponse;
import com.nnp.redmineintegration.api.model.response.ProjMembership;
import com.nnp.redmineintegration.api.model.response.ResponseProject;
import com.nnp.redmineintegration.api.model.response.ResponseUser;
import com.nnp.redmineintegration.api.model.response.TrackersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Tag(name = "Redmine Integration API", description = "Endpoints for managing Redmine users, projects, memberships, issues, and metadata")
@RequestMapping(path = "/api")
public interface RedmineAPI {

    @Operation(summary = "Create Redmine user", description = "Provisions a new user account in Redmine with credentials and profile details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(schema = @Schema(implementation = ResponseUser.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or user parameters"),
            @ApiResponse(responseCode = "500", description = "Redmine upstream API error or communication failure")
    })
    @PostMapping(path = "/createUser")
    ResponseEntity<ResponseUser> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details payload", required = true)
            @RequestBody User user,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Get list of users", description = "Retrieves a mapping of username (login) to user ID for all active users in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users list retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Redmine API communication error")
    })
    @GetMapping(path = "/getUsers")
    Map<String, Integer> getUsers(
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Check login existence", description = "Verifies whether a user login already exists in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boolean flag indicating if login exists")
    })
    @GetMapping(path = "/checkLoginExists")
    Boolean checkIfLoginExists(
            @Parameter(description = "User login name to check", required = true)
            @RequestParam String login,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get all projects", description = "Fetches a list of all accessible projects in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
    })
    @GetMapping(path = "/getProjects")
    List<com.nnp.redmineintegration.api.model.response.Project> getProjects(
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Check project existence", description = "Verifies whether a project identifier or name already exists in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boolean flag indicating if project exists")
    })
    @GetMapping(path = "/checkProjectExists")
    Boolean checkIfProjectExists(
            @Parameter(description = "Project name or identifier", required = true)
            @RequestParam String project,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get user API key", description = "Retrieves the Redmine REST API key for a specified user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API key string returned"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @GetMapping(path = "/getAPIKey")
    String getAPIKey(
            @Parameter(description = "Target user ID", required = true)
            String userId,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Delete user", description = "Deletes a user account from Redmine by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @DeleteMapping(path = "/deleteUser")
    ResponseEntity<String> deleteUser(
            @Parameter(description = "User ID to delete", required = true)
            @RequestParam String id,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Create project", description = "Creates a new project in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully", content = @Content(schema = @Schema(implementation = ResponseProject.class))),
            @ApiResponse(responseCode = "400", description = "Invalid project request payload"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @PostMapping(path = "/createProject")
    ResponseEntity<ResponseProject> createProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Project definition payload", required = true)
            @RequestBody Project project,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Delete project", description = "Deletes a project from Redmine by project ID or identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @DeleteMapping(path = "/deleteProject")
    ResponseEntity<String> deleteProject(
            @Parameter(description = "Project numeric ID (optional if identifier is provided)")
            @RequestParam(name = "id", required = false) String id,
            @Parameter(description = "Project string identifier (optional if ID is provided)")
            @RequestParam(name = "identifier", required = false) String identifier,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam(name = "apiKey") String apiKey
    );

    @Operation(summary = "Associate user with project", description = "Assigns a user to a project with specified role memberships in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User associated with project successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid membership payload"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @PostMapping(value = "/{projectId}/associateUser")
    ResponseEntity<String> associateUserWithProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Membership details with user ID and role IDs", required = true)
            Membership membership,
            @Parameter(description = "Project ID or identifier", required = true)
            @PathVariable String projectId,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get user details", description = "Fetches full JSON profile details for a specified user ID from Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details JSON string"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "/getUserDetail")
    ResponseEntity<String> getUserDetail(
            @Parameter(description = "User numeric ID", required = true)
            @RequestParam String id,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Onboard user", description = "Orchestrates user creation, project verification/creation, and role association in a single transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Onboarding completed successfully"),
            @ApiResponse(responseCode = "500", description = "Onboarding failure in user creation, project creation, or association")
    })
    @PostMapping(value = "/onboarduser")
    ResponseEntity<Boolean> onboardUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Onboarding request payload", required = true)
            @RequestBody OnboardUser onboardUser,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Create issue", description = "Creates a new issue / ticket in a Redmine project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Issue created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid issue payload"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @PostMapping(path = "/createIssue")
    ResponseEntity<String> createIssue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Issue payload with subject, description, tracker, and project ID", required = true)
            @RequestBody Issue issue,
            @Parameter(description = "Redmine user or admin API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Create support issue", description = "Creates a support issue / ticket for newly registered users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Support issue created successfully"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @PostMapping(path = "/createSupportIssue")
    ResponseEntity<String> createSupportIssue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Support issue payload", required = true)
            @RequestBody Issue issue,
            @Parameter(description = "Redmine support API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Get issues with filters", description = "Fetches a filtered list of issues based on project ID, author ID, and optional tracker ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of issues returned in JSON format")
    })
    @GetMapping(path = "/getIssues")
    ResponseEntity<String> getIssue(
            @Parameter(description = "Project ID", required = true)
            @RequestParam String projectId,
            @Parameter(description = "Author user ID", required = true)
            @RequestParam String authorId,
            @Parameter(description = "Tracker ID (optional)")
            @RequestParam(required = false) String trackerId,
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Update issue", description = "Updates fields, status, notes, or assignees of an existing Redmine issue.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue updated successfully"),
            @ApiResponse(responseCode = "404", description = "Issue not found"),
            @ApiResponse(responseCode = "500", description = "Upstream Redmine error")
    })
    @PutMapping(path = "/updateIssue")
    ResponseEntity<String> updateIssue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated issue details", required = true)
            @RequestBody Issue issue,
            @Parameter(description = "Issue ID to update", required = true)
            @RequestParam String issueId,
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get issue details", description = "Fetches full details of a specific issue by its issue ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue details JSON string"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @GetMapping(path = "/getIssuesDetails")
    ResponseEntity<String> getIssueDetails(
            @Parameter(description = "Issue numeric ID", required = true)
            @RequestParam String id,
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Create root project", description = "Provisions a root-level project, admin user, and assigns Manager membership with standard modules.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Root project provisioned successfully", content = @Content(schema = @Schema(implementation = RootProject.class))),
            @ApiResponse(responseCode = "500", description = "Root project or user provisioning error")
    })
    @PostMapping(path = "/createRootProject")
    ResponseEntity<RootProject> createRootProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Root project and admin user details", required = true)
            @RequestBody RootProject rootProject,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Get memberships for project", description = "Retrieves all user and group memberships and assigned roles for a given project ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of project memberships returned")
    })
    @GetMapping(path = "/getMembershipsForProj/{projId}")
    ResponseEntity<List<ProjMembership>> getMembershipsForProj(
            @Parameter(description = "Project numeric ID or identifier", required = true)
            @PathVariable String projId,
            @Parameter(description = "Redmine admin API key", required = true)
            @RequestParam String apiKey
    ) throws RedmineAPIException;

    @Operation(summary = "Get issue categories", description = "Retrieves configured issue categories for a project in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue categories response", content = @Content(schema = @Schema(implementation = IssueCategoriesResponse.class)))
    })
    @GetMapping(path = "/issue-categories")
    ResponseEntity<IssueCategoriesResponse> getIssueCategories(
            @Parameter(description = "Project ID", required = true)
            @RequestParam String projectId,
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get trackers", description = "Retrieves all issue trackers (e.g., Bug, Feature, Support) defined in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trackers response", content = @Content(schema = @Schema(implementation = TrackersResponse.class)))
    })
    @GetMapping(path = "/trackers")
    ResponseEntity<TrackersResponse> getTrackers(
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get issue statuses", description = "Retrieves all workflow issue statuses (e.g., New, In Progress, Resolved, Closed) defined in Redmine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue statuses response", content = @Content(schema = @Schema(implementation = IssueStatusesResponse.class)))
    })
    @GetMapping(path = "/issue-statuses")
    ResponseEntity<IssueStatusesResponse> getIssueStatuses(
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );

    @Operation(summary = "Get issue priorities", description = "Retrieves all issue priorities (e.g., Low, Normal, High, Urgent, Immediate) from Redmine enumerations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue priorities response", content = @Content(schema = @Schema(implementation = IssuePrioritiesResponse.class)))
    })
    @GetMapping(path = "/issue-priorities")
    ResponseEntity<IssuePrioritiesResponse> getIssuePriorities(
            @Parameter(description = "Redmine API key", required = true)
            @RequestParam String apiKey
    );
}