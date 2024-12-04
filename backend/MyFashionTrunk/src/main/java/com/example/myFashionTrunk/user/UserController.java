package com.example.myFashionTrunk.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    User getUser() {
        return null;
    }

    @PostMapping
    void createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
    }

    @PatchMapping("/{id}")
    void updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {
        //
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }


}
