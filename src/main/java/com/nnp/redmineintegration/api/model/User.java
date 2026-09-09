package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Top-level Redmine user payload wrapper.
 * Serializes to: {@code {"user": {...}}} as required by the Redmine REST API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "user"
})
@Setter
@Getter
@ToString
public class User {

    @JsonProperty("user")
    private UserRequest user;
}