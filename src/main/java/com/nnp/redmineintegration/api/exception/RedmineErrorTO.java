package com.nnp.redmineintegration.api.exception;

import lombok.Data;

@Data
public class RedmineErrorTO {
    private ErrorTO errorTO;

    public RedmineErrorTO(String status, String message) {
        super();
        this.errorTO = new ErrorTO(status, message);
    }
}
