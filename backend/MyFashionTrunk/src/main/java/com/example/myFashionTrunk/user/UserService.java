package com.example.myFashionTrunk.user;

import com.example.myFashionTrunk.category.CategoryRepository;
import com.example.myFashionTrunk.listing.ListingRepository;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void createUser(User user) {
        if(userRepo.existsByEmail(user.getEmail())) {
            throw new ValidationException("email address already in use");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepo.save(user);
    }

    public void authenticateUser(User user) {
       User existingUser = userRepo.findByEmail(user.getEmail())
               .orElseThrow(() -> new ValidationException("email address does not exist"));

        String userPassword = existingUser.getPassword();
        if(checkPassword(user, userPassword)) {
            throw new ValidationException("password does not match");
        }
    }

    public void deleteUser(Integer id) {
        //TODO: Delete Categories and Listings where Categories.user_id && Listings.user_id == user.id
    }


    private boolean checkPassword(User user, String hashedPassword) {
        return passwordEncoder.matches(user.getPassword(), hashedPassword);
    }
}
