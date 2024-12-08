package com.example.myFashionTrunk.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    ResponseEntity<?> getUser(@RequestBody UserRequest userRequest){
        return userService.authenticateUser(userRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }


}
