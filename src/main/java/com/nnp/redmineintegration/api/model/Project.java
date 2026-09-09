package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Top-level Redmine project payload wrapper.
 * Serializes to: {@code {"project": {...}}} as required by the Redmine REST API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "project"
})
@Setter
@Getter
@ToString
public class Project {

    @JsonProperty("project")
    private ProjectRequest project;
}