package com.example.myFashionTrunk.user;

import com.example.myFashionTrunk.category.CategoryRepository;
import com.example.myFashionTrunk.listing.ListingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for user-related operations
 * **/
@Service
public class UserService {
    private final UserRepository userRepo;
    private final CategoryRepository CategoryRepo;
    private final ListingRepository listingRepo;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService
     * @param userRepo repository for user entities
     * @param passwordEncoder bean that uses BCrypt for hashing
     * @param CategoryRepo repository for category entities
     * @param listingRepo repository for listing entities
     * **/
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder,
                       CategoryRepository CategoryRepo, ListingRepository listingRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.CategoryRepo = CategoryRepo;
        this.listingRepo = listingRepo;
    }

    /**
     * Creates a new user
     * @param userRequest the request object containing user details
     * @return ResponseEntity containing the user entity or an error message
     * **/
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

    /**
     * Authenticate user based on provided credentials
     * @param userRequest the user credentials for authentication
     * @return ResponseEntity Contains a user entity or an error message
     * **/
    public ResponseEntity<?> authenticateUser(UserRequest userRequest) {
        var errors = checkIfEmpty(userRequest, "login");
        if(!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
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

    /**
     * Updates existing user credentials
     * @param userRequest the user details for updating
     * @return ResponseEntity Contains a user result or an error message
     * **/
    public ResponseEntity<?> updateUser(UserRequest userRequest) {
        //validate userRequest object
        Map<String, String> errors = checkIfEmpty(userRequest, "update");
        if(!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Optional<User> existingUser = userRepo.findById(userRequest.getId());

        if(existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
        //create user and copy values from userRequest object to user entity
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
        if (!userRequest.getPassword().isEmpty()) {
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
            //hash the provided password and set it the user entity
            String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
            userRequest.setPassword(hashedPassword);
        }
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Deletes user based on their userId
     * @param userId the user id
     * **/
    public void deleteUser(Integer userId) {
        listingRepo.deleteAllByUserId(userId);
        CategoryRepo.deleteAllByUserId(userId);
        userRepo.deleteById(userId);
    }

    /**
     * Checks if any required field in the UserRequest object is empty
     * @param userRequest the request object containing user details
     * @param action the action to be performed (e.g. login, register...)
     * @return a map errors if any required fields are empty
     * **/
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


    /**
     * Checks if the provided password matches the user's stored password
     * @param user the user object
     * @param plainPassword the plain text password to check
     * @return true if password matches else false
     * **/
    private boolean checkPassword(User user, String plainPassword) {
        return passwordEncoder.matches(plainPassword, user.getPassword());
    }

    /**
     * Checks if the provided password meets required criteria
     * the criteria is at least one uppercase, one lowercase and one digit
     * @param plainPassword the plain text password to validate
     * @return true if password meets criteria else false
     * **/
    private boolean isPasswordValid(String plainPassword) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        return plainPassword.matches(regex);
    }
}
