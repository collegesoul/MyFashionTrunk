package com.example.myFashionTrunk.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * request object of user gotten from the client
 * **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private Integer id;
    private String name;
    private String surname;

    @Email(message = "invalid email format")
    private String email;

    @NotEmpty
    private String password;
}
