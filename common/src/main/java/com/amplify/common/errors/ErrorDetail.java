package com.amplify.common.errors;

import lombok.Data;

@Data
public class ErrorDetail {

    private String errorMessage;

    private Object details;
}
