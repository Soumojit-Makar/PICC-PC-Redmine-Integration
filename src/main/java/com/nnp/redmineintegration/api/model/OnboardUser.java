package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request payload for the orchestrated user onboarding operation ({@code POST /api/onboarduser}).
 * Combines user credentials, target project details, user type classification, and optional role IDs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnboardUser {

    @JsonProperty("login")
    private String login;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("password")
    private String password;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("identifier")
    private String identifier;

    /**
     * Determines whether a new project may be created during onboarding.
     * Use {@code "superAdmin"} to allow project creation; any other value restricts to existing projects only.
     */
    @JsonProperty("userType")
    private String userType;

    @JsonProperty("role_ids")
    private List<Integer> roleIds;
}
