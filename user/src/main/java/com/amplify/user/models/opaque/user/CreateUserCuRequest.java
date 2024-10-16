package com.amplify.user.models.opaque.user;

import com.amplify.common.enums.Gender;
import com.amplify.user.models.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserCuRequest {
    @NotNull
    private String firstName;
    private String givenName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    private String password;
    private Gender gender;
    private String address;
    @NotNull
    private LocalDate dateOfBirth;

    public User buldUser(){
        return User.builder()
                .firstName(firstName)
                .givenName(givenName)
                .gender(gender)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
