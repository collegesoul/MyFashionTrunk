package com.example.myFashionTrunk.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    User getUser(@RequestBody User user) throws AuthenticationException {
        return userService.authenticateUser(user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    void createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    void updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }


}
