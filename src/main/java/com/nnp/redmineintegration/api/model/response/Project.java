package com.nnp.redmineintegration.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

/**
 * Redmine project response model — maps the project object returned by the Redmine REST API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id", "name", "identifier", "description",
        "homepage", "status", "is_public", "inherit_members",
        "created_on", "updated_on"
})
@Getter
@Setter
public class Project {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("description")
    private Object description;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("is_public")
    private Boolean isPublic;

    @JsonProperty("inherit_members")
    private Boolean inheritMembers;

    @JsonProperty("created_on")
    private String createdOn;

    @JsonProperty("updated_on")
    private String updatedOn;
}
