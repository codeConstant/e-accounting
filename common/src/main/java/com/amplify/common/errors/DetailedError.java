package com.amplify.common.errors;

import org.springframework.http.HttpStatus;


public class DetailedError extends BaseError
{
    public DetailedError(HttpStatus code, String message, Object detail)
    {
        super(code, message, detail);
    }
    
    public DetailedError(HttpStatus code, String errorMessage)
    {
        super(code, errorMessage);
    }
}
