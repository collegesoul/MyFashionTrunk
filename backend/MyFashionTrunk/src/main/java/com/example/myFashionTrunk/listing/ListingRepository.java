package com.example.myFashionTrunk.listing;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends ListCrudRepository<Listing, Integer> {
    @Query("SELECT COUNT(l) FROM Listing l WHERE l.category.id = :categoryId")
    long CountByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("DELETE FROM Listing l WHERE l.user.id = :userId ")
    void deleteAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT l FROM Listing l WHERE l.user.id = :userId")
    List<Listing> findAllByUserId(@Param("userId") Integer userId);
}
