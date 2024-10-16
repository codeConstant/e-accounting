package com.amplify.common.errors;

import org.springframework.http.HttpStatus;


public class BaseError extends RuntimeException
{
    private final HttpStatus status;
    private final String     errorMessage;
    private final Object     details;
    
    public BaseError(HttpStatus status, String errorMessage)
    {
        this(status, errorMessage, null);
    }
    
    public BaseError(String errorMessage)
    {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }
    
    public BaseError(HttpStatus status, String errorMessage, Object details)
    {
        super(errorMessage);
        this.status       = status;
        this.errorMessage = errorMessage;
        this.details      = details;
    }
    
    public BaseError(Exception ex)
    {
        this.status       = HttpStatus.BAD_REQUEST;
        this.errorMessage = ex.getMessage();
        this.details      = ex;
    }
    
    public HttpStatus getStatus ()
    {
        return status;
    }
    
    public String getErrorMessage ()
    {
        return errorMessage;
    }
    
    public Object getDetails ()
    {
        return details;
    }
    
    public ErrorCompact toCompact ()
    {
        return new ErrorCompact(status, errorMessage, getLocalizedMessage(), getCause(), details);
    }
}
