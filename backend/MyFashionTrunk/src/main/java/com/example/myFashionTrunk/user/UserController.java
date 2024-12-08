package com.example.myFashionTrunk.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller for handling user-related operations
 * **/
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticate user based on provided credentials
     * @param userRequest the user credentials for authentication
     * @return ResponseEntity Contains a user entity or an error message
     * **/
    @PostMapping("/login")
    ResponseEntity<?> getUser(@RequestBody UserRequest userRequest){
        return userService.authenticateUser(userRequest);
    }

    /**
     * Creates a new user
     * @param userRequest the user details for registration
     * @return ResponseEntity Contains a user entity or an error message
     * **/
    @PostMapping("/register")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    /**
     * Updates existing user credentials
     * @param userRequest the user details for updating
     * @return ResponseEntity Contains a user result or an error message
     * **/
    @PutMapping
    ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    /**
     * Deletes user based on their userId
     * @param id the user id
     * **/
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }


}
