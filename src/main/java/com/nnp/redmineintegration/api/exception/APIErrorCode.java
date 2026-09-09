package com.nnp.redmineintegration.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum APIErrorCode {
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "Item Not Found"),
    ITEM_NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "Already present. Insert/Update not possible."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Internal Error"),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Parameters Passes");

    private final HttpStatus httpStatus;
    private final String message;
}