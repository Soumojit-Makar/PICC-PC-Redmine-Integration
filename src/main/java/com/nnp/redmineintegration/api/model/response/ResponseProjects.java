package com.nnp.redmineintegration.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "projects",
        "total_count",
        "offset",
        "limit"
})
public class ResponseProjects {

    @JsonProperty("projects")
    public List<Project> projects ;
    @JsonProperty("total_count")
    public Integer totalCount;
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("limit")
    public Integer limit;

}