package com.example.myFashionTrunk.user;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * User Repository for performing CRUD operations on User entities
 * **/
public interface UserRepository extends ListCrudRepository<User, Integer> {
    /**
     * Checks if user exists by their email address
     * @param email the email address to check
     * @return true if user exist by the specific email else false
     * **/
    boolean existsByEmail(String email);
    /**
     * Find a user by their email address
     * @param email the email address to search
     * @return An Optional containing user or an empty optional
     * **/
    Optional<User> findByEmail(String email);
}
