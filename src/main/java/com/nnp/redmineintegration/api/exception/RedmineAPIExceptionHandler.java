package com.nnp.redmineintegration.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RedmineAPIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RedmineAPIException.class)
    protected ResponseEntity<Object> handleConflict(RedmineAPIException redmineAPIException, WebRequest webRequest) {

        RedmineErrorTO redmineErrorTO = new RedmineErrorTO(String.valueOf(redmineAPIException.getStatus()), redmineAPIException.getMessage());
        return handleExceptionInternal(redmineAPIException, redmineErrorTO, new HttpHeaders(), HttpStatus.valueOf(Integer.parseInt(redmineErrorTO.getErrorTO().getStatus())), webRequest);
    }
}
