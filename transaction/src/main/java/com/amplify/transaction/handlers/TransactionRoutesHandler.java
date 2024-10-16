package com.amplify.transaction.handlers;

import com.amplify.common.enums.Messages;
import com.amplify.common.enums.TransactionType;
import com.amplify.common.errors.DetailedError;
import com.amplify.common.errors.ErrorResponse;
import com.amplify.common.handler.BaseRoutesHandler;
import com.amplify.common.models.ResponseModel;
import com.amplify.common.models.TransferAmountRequest;
import com.amplify.transaction.models.entity.Transaction;
import com.amplify.transaction.models.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Log4j2
public final class TransactionRoutesHandler extends BaseRoutesHandler {

    @Value("${core.user.api}")
    private String userApi;
    private final  WebClient.Builder client;

    private final TransactionService transactionService;

    public TransactionRoutesHandler(Validator validator,WebClient.Builder client,TransactionService transactionService) {
        super(validator);
        this.client = client;
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> transferAmount(ServerRequest request) {
        var transferAmountRequestMono = request.bodyToMono(TransferAmountRequest.class);
        return transferAmountRequestMono.flatMap(transferAmountRequest -> {
            return _send(transferAmountRequest)
                    .bodyToMono(ResponseModel.class)
                    .flatMap(response -> {
                        if(response.getMessage().equals(Messages.SUCCESS)){
                           var senderTransaction  = buildTransaction(TransactionType.DEBIT, transferAmountRequest.getAmount(), transferAmountRequest.getSenderAccountNumber());
                           var receiverTransaction  = buildTransaction(TransactionType.CREDIT, transferAmountRequest.getAmount(), transferAmountRequest.getRecipientsAccountNumber());
                           var transactions = transactionService.saveAll(List.of(senderTransaction,receiverTransaction));
                           return this.successResponse(transactions);
                        }
                        return Mono.error(new DetailedError(HttpStatus.BAD_REQUEST, Messages.VALIDATION_FAILED));
                    });
        });
    }

    private Transaction buildTransaction(TransactionType transactionType, BigDecimal amount, String accountNumber){
        return Transaction.builder()
                .transactionType(transactionType)
                .amount(amount)
                .accountNumber(accountNumber)
                .dateTime(LocalDateTime.now())
                .build();
    }

    private WebClient.ResponseSpec _send(TransferAmountRequest transferAmountRequest) {
        return client
                .build()
                .method(HttpMethod.POST)
                .uri(userApi+"account/transferAmount")
                .body(BodyInserters.fromValue(transferAmountRequest))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(ErrorResponse.class)
                            .flatMap(errorBody -> {
                                return Mono.error(new DetailedError(HttpStatus.BAD_REQUEST, errorBody.getMessage()));
                            });
                });
    }
}
