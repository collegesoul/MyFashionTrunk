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

    public User authenticateUser(UserRequest userRequest) throws AuthenticationException {
       User existingUser = userRepo.findByEmail(userRequest.getEmail())
               .orElseThrow(() -> new EntityNotFoundException("email address does not exist"));

        if(checkPassword(existingUser, userRequest.getPassword())) {
            throw new AuthenticationException("password does not match");
        }
        return existingUser;
    }

    public User updateUser(UserRequest userRequest) {
        User existingUser = userRepo.findById(userRequest.getId()).orElseThrow(() ->new EntityNotFoundException("user does not exist"));
        if(!existingUser.getName().equals(userRequest.getName())) {
            existingUser.setName(userRequest.getName());
        }
        if(!existingUser.getSurname().equals(userRequest.getSurname())) {
            existingUser.setSurname(userRequest.getSurname());
        }
        if(!existingUser.getEmail().equals(userRequest.getEmail())) {
            existingUser.setEmail(userRequest.getEmail());
        }
        if (!Objects.equals(userRequest.getPassword(), "") && !Objects.equals(userRequest.getPassword(), existingUser.getPassword())) {
            String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
            userRequest.setPassword(hashedPassword);
        }
        userRepo.save(existingUser);
        return existingUser;
    }

    public void deleteUser(Integer userId) {
        listingRepo.deleteAllByUserId(userId);
        CategoryRepo.deleteAllByUserId(userId);
        userRepo.deleteById(userId);
    }


    private boolean checkPassword(User user, String plainPassword) {
        return passwordEncoder.matches(plainPassword, user.getPassword());
    }
}
