package com.nnp.redmineintegration.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedmineAPIException extends RuntimeException {
    private int status;
    private String message;

    public RedmineAPIException() {
        super();
    }

    public RedmineAPIException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public RedmineAPIException(APIErrorCode apiErrorCode) {
        this.status = apiErrorCode.getHttpStatus().value();
        this.message = apiErrorCode.getMessage();
    }
}