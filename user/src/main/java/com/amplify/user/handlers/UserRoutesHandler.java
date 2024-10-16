package com.amplify.user.handlers;

import com.amplify.common.handler.BaseRoutesHandler;
import com.amplify.user.models.opaque.user.CreateUserRequest;
import com.amplify.user.models.opaque.user.UpdateUserRequest;
import com.amplify.user.models.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Service
@Log4j2
public final class UserRoutesHandler extends BaseRoutesHandler {
    private final UserService userService;

    public UserRoutesHandler(Validator validator, UserService userService) {
        super(validator);
        this.userService = userService;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        var userRequestMono = request.bodyToMono(CreateUserRequest.class);
        return userRequestMono.flatMap(createUserRequest -> {
            var errors = validateObject(createUserRequest);
            if (errors.hasErrors()) {
                return validationFailedResponse(errors);
            }
            var user = userService.saveUser(createUserRequest.buldUser());
            return this.successResponse(user);
        });
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        var userId = getPathVariableId(request);
        var user = userService.findById((long) Integer.parseInt(userId));
        return this.successResponse(user);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        var userList = userService.findByAll();
        return this.successResponse(userList);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        var userRequestMono = request.bodyToMono(UpdateUserRequest.class);
        return userRequestMono.flatMap(updateUserRequest  -> {
            var user = userService.update(updateUserRequest);
            return this.successResponse(user);
        });
    }
}
