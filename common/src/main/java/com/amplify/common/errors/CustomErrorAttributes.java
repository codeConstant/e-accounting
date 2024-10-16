package com.amplify.common.errors;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = getError(request);
        var formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if (error instanceof ValidationError ex) {
            response.put("status", 400);
            response.put("message", ex.getMessage());
           // errorMap.put("details", ex.getDetails());
        } else if (error instanceof DataIntegrityViolationException ex) {
            response.put("status", 400);
            response.put("message", ex.getMessage());
           // errorMap.put("details", dataIntegrityViolationException.getMessage());
        }
        else if(error instanceof WebClientException ex){
            response.put("status", 400);
            response.put("message", ex.getMessage());
        }
        else if (error instanceof DetailedError ex) {
            response.put("status", ex.getStatus().value());
            response.put("message", ex.getMessage());
          //  errorMap.put("details", ex.getDetails());
        }
        else if (error instanceof BaseError ex) {
            HttpStatus status = ex.getStatus();
            response.put("status", status.value());
        } else {
            errorMap.put("details", error);
        }

        errorMap.put("errorMessage", error.getMessage());
        response.put("message", error.getMessage());
        response.put("error", errorMap);

        response.put("timestamp", formatter.format(new Date()));
        return response;
    }
}