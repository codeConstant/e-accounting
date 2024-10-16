package com.amplify.user.config;


import com.amplify.user.handlers.UserAccountRoutesHandler;
import com.amplify.user.handlers.UserRoutesHandler;
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

    private final UserRoutesHandler userRoutesHandler;

    private final UserAccountRoutesHandler userAccountRoutesHandler;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .POST("/user", userRoutesHandler::create)
                .GET("/user/{id}", userRoutesHandler::findOne)
                .GET("/user", userRoutesHandler::findAll)
                .PUT("/user", userRoutesHandler::update)

                .POST("/account", userAccountRoutesHandler::createAccount)
                .PUT("/account/status-update", userAccountRoutesHandler::statusUpdate)
                .GET("/account/findOne", userAccountRoutesHandler::findOne)
                .GET("/account/findALL", userAccountRoutesHandler::findAll)
                .POST("/account/transferAmount", userAccountRoutesHandler::transferAmount)
                .GET("/account/getAccountDetails/{id}", userAccountRoutesHandler::getAccountDetails)
                .PUT("/account/balanceUpdate", userAccountRoutesHandler::balanceUpdate)
                .build();
    }
}
