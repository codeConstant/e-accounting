package com.amplify.common.handler;


import com.amplify.common.enums.Messages;
import com.amplify.common.errors.NotFoundError;
import com.amplify.common.errors.ValidationError;
import com.amplify.common.models.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class BaseRoutesHandler
{
    protected final Validator validator;

//    @Autowired
//    protected ApplicationEventPublisher eventPublisher;

    protected <T> Mono<ServerResponse> successResponse (T body)
    {
        return successResponse(body, Messages.SUCCESS);
    }

    protected <T> Mono<ServerResponse> successResponse(T body, String messages) {
        return ServerResponse.ok()
                .bodyValue(new ResponseModel<>(messages, body, null));
    }
    
    protected Mono<ServerResponse> notFoundResponse (String detailMessage)
    {
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                             .bodyValue(new ResponseModel<>(Messages.NOT_FOUND, null, new NotFoundError(detailMessage).toCompact()));
    }

    protected Mono<ServerResponse> validationFailedResponse (String detailMessage)
    {
        return Mono.error(new ValidationError(detailMessage))
                   .flatMap(err -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                                 .bodyValue(new ResponseModel<>(Messages.VALIDATION_FAILED, null, ((ValidationError) err).toCompact())));
    }

    protected Mono<ServerResponse> validationFailedResponse (Errors errors)
    {
        return Mono.error(new ValidationError("Validation failed", formatValidationErrors(errors)))
                   .flatMap(err -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                                 .bodyValue(new ResponseModel<>(Messages.VALIDATION_FAILED, null, ((ValidationError) err).toCompact())));
    }
    
    protected Map<String, Object> formatValidationErrors (Errors errors)
    {
        Map<String, Object> validationErrors = new HashMap<>();
        for (ObjectError error : errors.getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            else {
                validationErrors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return validationErrors;
    }
    
    protected String getPathVariableId (ServerRequest request)
    {
        return request.pathVariable("id");
    }
    
    protected <T> Errors validateObject (T object)
    {
        Errors errors = new BeanPropertyBindingResult(object,
                                                      object.getClass()
                                                            .getSimpleName());
        ValidationUtils.invokeValidator(validator, object, errors);
        return errors;
    }
    
    public <T> void validateObjectNew (T object)
    {
        Errors errors = new BeanPropertyBindingResult(object,
                                                      object.getClass()
                                                            .getSimpleName());
        ValidationUtils.invokeValidator(validator, object, errors);
        if (errors.hasErrors()) {
            throw new ValidationError(Messages.VALIDATION_FAILED, formatValidationErrors(errors));
        }
    }
    
//    public Mono<UserDto> authenticationPrincipal ()
//    {
//        return ReactiveSecurityContextHolder.getContext()
//                                            .flatMap(securityContext -> Mono.just((UserDto) securityContext.getAuthentication()
//                                                                                                           .getPrincipal()))
//                                            .switchIfEmpty(Mono.just(new UserDto()));
//    }
    
    public <T> Mono<List<T>> fluxExtension (Flux<T> objFlux)
    {
        return objFlux.collectList()
                      .flatMap(obj -> {
                          if (obj.isEmpty()) {
                              return Mono.empty();
                          }
                          else {
                              return Mono.just(obj);
                          }
                      });
    }
    
//    public Mono<UserDetails> authenticationPrincipal1 ()
//    {
//        return ReactiveSecurityContextHolder.getContext()
//                                            .flatMap(securityContext -> Mono.just((UserDetails) securityContext.getAuthentication()
//                                                                                                               .getPrincipal()))
//                                            .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")));
//    }
//
//    protected void authorize(UserDto userDto, Function<UserDto, Boolean> resolver) {
//        var x = resolver.apply(userDto);
//        if (!x) {
//            throw new DetailedError(HttpStatus.FORBIDDEN, Messages.UNAUTHORIZED);
//        }
//    }
//
//    public Pageable pageable(final ServerRequest request) {
//        return this.pageable(request, false);
//    }
//
//    public Pageable pageable(final ServerRequest request, boolean isCsv) {
//        var pageNum = request.queryParam("pageNum").map(Integer::parseInt).orElse(0);
//        var pageSize = request.queryParam("pageSize").map(Integer::parseInt).orElse(isCsv ? 50000 : 10);
//        return PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "id"));
//    }

    //    protected Mono<GenericAPIResponse<?>> interServiceRequest(Mono<Object> defaultResponse, ParameterizedTypeReference<?> typeReference) {
//        return defaultResponse.flatMap(response -> {
//            @SuppressWarnings("unchecked")
//            var responseFinal = (HashMap<String, Object>) response;
//            var objectMapper = new ObjectMapper();
//            JavaType javaType = TypeFactory.defaultInstance().constructType(typeReference.getType());
//            return Mono.just(objectMapper.convertValue(responseFinal, javaType));
//        });
//    }
}
