package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Inner payload object for Redmine user creation and provisioning operations.
 * Wraps the fields that map directly to the Redmine REST API {@code /users.json} body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class UserRequest {

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

    @JsonProperty("api_key")
    private String apiKey;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
