package com.example.myFashionTrunk.user;

import com.example.myFashionTrunk.category.CategoryRepository;
import com.example.myFashionTrunk.listing.ListingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final CategoryRepository CategoryRepo;
    private final ListingRepository listingRepo;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder,
                       CategoryRepository CategoryRepo, ListingRepository listingRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.CategoryRepo = CategoryRepo;
        this.listingRepo = listingRepo;
    }

    public ResponseEntity<?> createUser(UserRequest userRequest) {
        Map<String, String> errors = checkIfEmpty(userRequest, "register");
        if (!errors.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        User newUser = new User();
        if(userRepo.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Email address already in use",
                            "field", "email"
                    ));
        }
        if(userRequest.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Password must be at least 8 characters",
                            "field", "password"
                    ));
        } else if(!isPasswordValid(userRequest.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Password must contain at least one Uppercase letter, one Lowercase letter and one digit",
                            "field", "password"
                    ));
        }
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        newUser.setName(userRequest.getName());
        newUser.setSurname(userRequest.getSurname());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(hashedPassword);

        userRepo.save(newUser);
        UserResponse userResponse = new UserResponse(
                newUser.getId(),
                newUser.getName(),
                newUser.getSurname(),
                newUser.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    public ResponseEntity<?> authenticateUser(UserRequest userRequest) {
        var errors = checkIfEmpty(userRequest, "login");
        if(!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
       Optional<User> existingUser = userRepo.findByEmail(userRequest.getEmail());
       if(existingUser.isEmpty()) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                   .body(Map.of(
                           "error", "Invalid username or password"
                   ));
       }
       User user = existingUser.get();

        if(!checkPassword(user, userRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", "Invalid username or password"
                    ));
        }
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
        return ResponseEntity.ok(userResponse);
    }

    public ResponseEntity<?> updateUser(UserRequest userRequest) {
        Map<String, String> errors = checkIfEmpty(userRequest, "update");
        if(!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        Optional<User> existingUser = userRepo.findById(userRequest.getId());

        if(existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", "User not found"
                    ));
        }
        if(userRepo.existsByEmail(userRequest.getEmail()) && !userRequest.getEmail().equals(existingUser.get().getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Email address already in use",
                            "field", "email"
                    ));
        }

        User user = existingUser.get();

        if(!user.getName().equals(userRequest.getName())) {
            user.setName(userRequest.getName());
        }
        if(!user.getSurname().equals(userRequest.getSurname())) {
            user.setSurname(userRequest.getSurname());
        }
        if(!user.getEmail().equals(userRequest.getEmail())) {
            user.setEmail(userRequest.getEmail());
        }
        if (!Objects.equals(userRequest.getPassword(), "")) {
            if(userRequest.getPassword().length() < 8) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "error", "Password must be at least 8 characters",
                                "field", "password"
                        ));
            } else if(!isPasswordValid(userRequest.getPassword())){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "error", "Password must contain at least one Uppercase letter, " +
                                        "one Lowercase letter and one digit",
                                "field", "password"
                        ));
            }
            String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
            userRequest.setPassword(hashedPassword);
        }
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    public void deleteUser(Integer userId) {
        listingRepo.deleteAllByUserId(userId);
        CategoryRepo.deleteAllByUserId(userId);
        userRepo.deleteById(userId);
    }

    private Map<String, String> checkIfEmpty(UserRequest userRequest, String action) {
        Map<String, String> errors = new HashMap<>();
        switch (action) {
            case "login" -> {
                if (userRequest.getEmail().isEmpty()) {
                    errors.put("email", "Email is required");
                }
                if (userRequest.getPassword().isEmpty()) {
                    errors.put("password", "Password is required");
                }
            }
            case "register" -> {
                if (userRequest.getName().isEmpty()) {
                    errors.put("name", "Name is required");
                }
                if (userRequest.getSurname().isEmpty()) {
                    errors.put("surname", "Surname is required");
                }
                if (userRequest.getEmail().isEmpty()) {
                    errors.put("email", "Email is required");
                }
                if (userRequest.getPassword().isEmpty()) {
                    errors.put("password", "Password is required");
                }
            }
            case "update" -> {
                if (userRequest.getName().isEmpty()) {
                    errors.put("name", "Name is required");
                }
                if (userRequest.getSurname().isEmpty()) {
                    errors.put("surname", "Surname is required");
                }
                if (userRequest.getEmail().isEmpty()) {
                    errors.put("email", "Email is required");
                }
            }
        }
        return errors;
    }


    private boolean checkPassword(User user, String plainPassword) {
        return passwordEncoder.matches(plainPassword, user.getPassword());
    }

    private boolean isPasswordValid(String plainPassword) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        return plainPassword.matches(regex);
    }
}
