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
 * Top-level Redmine issue payload wrapper.
 * Serializes to: {@code {"issue": {...}}} as required by the Redmine REST API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "issue"
})
@Getter
@Setter
public class Issue {

    @JsonProperty("issue")
    private IssueRequest issue;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
