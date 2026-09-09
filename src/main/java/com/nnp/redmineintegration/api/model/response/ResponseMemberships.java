/**
 * ResponseMemberships.java
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
 * ResponseMemberships.java
 *
 * @author Arnab Chatterjee
 * @date 30-Apr-2025
 */
@Getter
@Setter
@ToString
public class ResponseMemberships {
	@JsonProperty("memberships")
	private List<ProjMembership> memberships;

}
