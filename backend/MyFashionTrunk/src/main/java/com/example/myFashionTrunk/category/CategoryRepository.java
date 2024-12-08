package com.example.myFashionTrunk.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Category Repository for performing CRUD operations on Category entities
 * **/
public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    /**
     * Deletes all categories associated to a specific user
     * @param userId the id of the user
     * **/
    @Query("DELETE FROM Category c WHERE c.user.id = :userId ")
    void deleteAllByUserId(@Param("userId") Integer userId);
}
