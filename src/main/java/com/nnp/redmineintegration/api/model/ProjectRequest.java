package com.nnp.redmineintegration.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Inner payload object for Redmine project creation operations.
 * Wraps the fields that map directly to the Redmine REST API {@code /projects.json} body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "identifier",
        "description",
        "is_public",
        "parent_id",
        "inherit_members",
        "tracker_ids",
        "enabled_module_names",
        "custom_field_values"
})
@Setter
@Getter
@ToString
public class ProjectRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_public")
    private Boolean isPublic;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("inherit_members")
    private Boolean inheritMembers;

    @JsonProperty("tracker_ids")
    private List<Integer> trackerIds = null;

    @JsonProperty("enabled_module_names")
    private List<String> enabledModuleNames = null;

    @JsonProperty("custom_field_values")
    private Map<String, String> customFieldValMap = null;
}
