package com.example.myFashionTrunk.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * response object of user sent to the client
 * It doesn't include the user password for security
 * **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private int id;
    private String name;
    private String surname;
    private String email;
}
