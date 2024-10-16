package com.amplify.common.errors;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class ApiExceptionHandler extends AbstractErrorWebExceptionHandler
{
    public ApiExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer)
    {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(configurer.getReaders());
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction (ErrorAttributes errorAttributes)
    {
        return RouterFunctions.route(RequestPredicates.all(), this::renderException);
    }

    private Mono<ServerResponse> renderException (ServerRequest request)
    {
        Map<String, Object> error = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        final int           statusCode = getStatus(error);
        error.remove("status");
        return ServerResponse.status(statusCode)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromValue(error));
    }

    private int getStatus (final Map<String, Object> error)
    {
        try {
            var statusCode = error.get("status")
                                  .toString();
            return Integer.parseInt(statusCode);
        }
        catch (Exception ex) {
            return 500;
        }
    }
}
