package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Inner payload object for Redmine project membership (user-role association) operations.
 * Wraps the fields that map directly to the Redmine REST API
 * {@code /projects/{id}/memberships.json} body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "user_id",
        "role_ids"
})
@Setter
@Getter
@ToString
public class MembershipRequest {

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("role_ids")
    private List<Integer> roleIds = null;
}
