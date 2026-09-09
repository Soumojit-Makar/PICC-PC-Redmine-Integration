package com.nnp.redmineintegration.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorTO {
    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;
}
