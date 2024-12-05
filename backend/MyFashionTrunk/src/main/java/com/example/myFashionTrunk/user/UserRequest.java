package com.example.myFashionTrunk.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "surname is required")
    private String surname;

    @Email(message = "invalid email format")
    @NotBlank(message = "email is required")
    private String email;

    @NotEmpty
    @Size(min = 8, max = 15, message = "Password must be between 8 - 15 characters")
    private String password;
}
