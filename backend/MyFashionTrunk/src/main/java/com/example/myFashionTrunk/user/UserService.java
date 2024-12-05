package com.example.myFashionTrunk.user;

import com.example.myFashionTrunk.category.CategoryRepository;
import com.example.myFashionTrunk.listing.ListingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Objects;

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

    public User createUser(UserRequest userRequest) {
        User newUser = new User();
        if(userRepo.existsByEmail(userRequest.getEmail())) {
            throw new ValidationException("email address already in use");
        }
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        newUser.setName(userRequest.getName());
        newUser.setSurname(userRequest.getSurname());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(hashedPassword);

        userRepo.save(newUser);
        return newUser;
    }

    public User authenticateUser(User user) throws AuthenticationException {
       User existingUser = userRepo.findByEmail(user.getEmail())
               .orElseThrow(() -> new EntityNotFoundException("email address does not exist"));

        String userPassword = existingUser.getPassword();
        if(checkPassword(user, userPassword)) {
            throw new AuthenticationException("password does not match");
        }
        return existingUser;
    }

    public void updateUser(Integer userId, User user) {
        User existingUser = userRepo.findById(userId).orElseThrow(() ->new EntityNotFoundException("user does not exist"));
        if(!existingUser.getName().equals(user.getName())) {
            existingUser.setName(user.getName());
        }
        if(!existingUser.getSurname().equals(user.getSurname())) {
            existingUser.setSurname(user.getSurname());
        }
        if(!existingUser.getEmail().equals(user.getEmail())) {
            existingUser.setEmail(user.getEmail());
        }
        if (!Objects.equals(user.getPassword(), "")) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }
        userRepo.save(existingUser);
    }

    public void deleteUser(Integer userId) {
        listingRepo.deleteAllByUserId(userId);
        CategoryRepo.deleteAllByUserId(userId);
        userRepo.deleteById(userId);
    }


    private boolean checkPassword(User user, String hashedPassword) {
        return passwordEncoder.matches(user.getPassword(), hashedPassword);
    }
}
