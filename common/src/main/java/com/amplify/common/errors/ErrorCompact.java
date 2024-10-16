package com.amplify.common.errors;
import org.springframework.http.HttpStatus;


public record ErrorCompact(
        HttpStatus status,
        String errorMessage,
        String localizedMessage,
        Throwable cause,
        Object details) {}