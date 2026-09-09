/**
 * Membership.java
 *
 * @author Arnab Chatterjee
 * @date 30-Apr-2025
 */
package com.nnp.redmineintegration.api.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Membership.java
 *
 * @author Arnab Chatterjee
 * @date 30-Apr-2025
 */
@Getter
@Setter
@ToString
public class ProjMembership {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("project")
    private Project project;
    @JsonProperty("user")
    private User user;
    @JsonProperty("roles")
    private List<Role> roles;
    

}
