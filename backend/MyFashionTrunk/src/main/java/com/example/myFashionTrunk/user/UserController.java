package com.example.myFashionTrunk.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    ResponseEntity<?> getUser(@RequestBody UserRequest userRequest){
        return userService.authenticateUser(userRequest);
    }

    @PostMapping("/register")
    ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

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
