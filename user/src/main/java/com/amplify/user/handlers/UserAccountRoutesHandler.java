package com.amplify.user.handlers;

import com.amplify.common.enums.Messages;
import com.amplify.common.handler.BaseRoutesHandler;
import com.amplify.common.models.TransferAmountRequest;
import com.amplify.user.models.opaque.transaction.AccountCreateRequest;
import com.amplify.user.models.opaque.transaction.StatusUpdateRequest;
import com.amplify.user.models.service.AccountService;
import com.amplify.user.models.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Service
@Log4j2
public final class UserAccountRoutesHandler extends BaseRoutesHandler {
    private final AccountService accountService;
    private final UserService userService;

    public UserAccountRoutesHandler(Validator validator, AccountService accountService, UserService userService) {
        super(validator);
        this.accountService = accountService;
        this.userService = userService;
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        var accountCreateRequestMono = request.bodyToMono(AccountCreateRequest.class);
        return accountCreateRequestMono.flatMap(accountCreateRequest -> {
            var errors = validateObject(accountCreateRequest);
            if (errors.hasErrors()) {
                return validationFailedResponse(errors);
            }
            var user = userService.findById(accountCreateRequest.getUserId());
            var account = accountCreateRequest.buildAccount(user);
            return this.successResponse(accountService.saveAccount(account));
        });
    }

    public Mono<ServerResponse> statusUpdate(ServerRequest request) {
        var statusUpdateRequestMono = request.bodyToMono(StatusUpdateRequest.class);
        return statusUpdateRequestMono.flatMap(statusUpdateRequest -> {
            var errors = validateObject(statusUpdateRequest);
            if (errors.hasErrors()) {
                return validationFailedResponse(errors);
            }
            var account = accountService.findById(statusUpdateRequest.getId());
            account.setAccountStatus(statusUpdateRequest.getAccountStatus());
            return this.successResponse(accountService.saveAccount(account));
        });
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        var id = getPathVariableId(request);
        var user = accountService.findById((long) Integer.parseInt(id));
        return this.successResponse(user);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        var userList = accountService.findByAll();
        return this.successResponse(userList);
    }

    public Mono<ServerResponse> getAccountDetails(ServerRequest request) {
        var accountNumber = getPathVariableId(request);
        var account = accountService.findByAccountNumber(accountNumber);
        return this.successResponse(account);
    }

    public Mono<ServerResponse> transferAmount(ServerRequest request) {
        var transferAmountRequestMono = request.bodyToMono(TransferAmountRequest.class);
        return transferAmountRequestMono.flatMap(transferAmountRequest -> {
            var errors = validateObject(transferAmountRequest);
            if (errors.hasErrors()) {
                return validationFailedResponse(errors);
            }
            var isTransferSuccessfully = accountService.transferAmount(transferAmountRequest);
            return this.successResponse(isTransferSuccessfully ? Messages.SUCCESSFULLY_TRANSFERRED : Messages.TRANSFERRED_FAIL);
        });
    }
}
