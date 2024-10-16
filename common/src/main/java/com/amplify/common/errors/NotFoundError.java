package com.amplify.common.errors;

import org.springframework.http.HttpStatus;

public class NotFoundError extends BaseError {
    
    public NotFoundError(String errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }
    
    public NotFoundError(String errorMessage, Object details) {
        super(HttpStatus.NOT_FOUND, errorMessage, details);
    }
    
    public static NotFoundError withMessage(String errorMessage) {
        return new NotFoundError(errorMessage);
    }
}
