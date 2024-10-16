package com.amplify.transaction.config;


import com.amplify.transaction.handlers.TransactionRoutesHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
@ComponentScan("com.amplify.common")
@AllArgsConstructor
public class AppRouterConfig {

    private final TransactionRoutesHandler transactionRoutesHandler;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .POST("/transaction/transferAmount", transactionRoutesHandler::transferAmount)
                .build();
    }
}
