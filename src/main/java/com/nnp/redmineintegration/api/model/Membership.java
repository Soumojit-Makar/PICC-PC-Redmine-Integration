package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Top-level Redmine membership payload wrapper.
 * Serializes to: {@code {"membership": {...}}} as required by the Redmine REST API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "membership"
})
@Setter
@Getter
@ToString
public class Membership {

    @JsonProperty("membership")
    private MembershipRequest membership;
}