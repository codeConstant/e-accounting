package com.amplify.user.models.entities;

import com.amplify.common.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TBL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String givenName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String address;
    private LocalDate dateOfBirth;


    public String getFullName(){
        return firstName + " "+ givenName;
    }
}
