package com.example.myFashionTrunk.listing;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Listing Repository for performing CRUD operations on Listing entities
 * **/
public interface ListingRepository extends ListCrudRepository<Listing, Integer> {
    /**
     * Counts the number of listings in a specific category
     * @param categoryId the id of the category
     * @return the number of listings in the specified category
     * **/
    @Query("SELECT COUNT(l) FROM Listing l WHERE l.category.id = :categoryId")
    long CountByCategoryId(@Param("categoryId") Integer categoryId);

    /**
     * Deletes all listings associated to a specific user
     * @param userId the id of the user
     * **/
    @Query("DELETE FROM Listing l WHERE l.user.id = :userId ")
    void deleteAllByUserId(@Param("userId") Integer userId);

    /**
     * Gets all listings associated to a specific user
     * @param userId the id of the user
     * @return a List of Listings associated to a specified user
     * **/
    @Query("SELECT l FROM Listing l WHERE l.user.id = :userId")
    List<Listing> findAllByUserId(@Param("userId") Integer userId);
}
