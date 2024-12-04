package com.example.myFashionTrunk.listing;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface ListingRepository extends ListCrudRepository<Listing, Integer> {
    @Query("SELECT COUNT(l) FROM Listing l WHERE l.category.id = :categoryId")
    long CountByCategoryId(@Param("categoryId") Integer categoryId);
}
