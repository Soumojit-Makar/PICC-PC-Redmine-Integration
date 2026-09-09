package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Inner payload object for Redmine issue creation and update operations.
 * Wraps the fields that map directly to the Redmine REST API {@code /issues.json} body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "subject",
        "notes"
})
@Getter
@Setter
public class IssueRequest {

    @JsonProperty("parent_issue_id")
    private Integer parentIssueId;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("priority_id")
    private Integer priorityId;

    @JsonProperty("tracker_id")
    private Integer trackerId;

    @JsonProperty("status_id")
    private Integer statusId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("author_id")
    private String authorId;

    @JsonProperty("assigned_to_id")
    private String assignedToId;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("notes")
    private String notes;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
