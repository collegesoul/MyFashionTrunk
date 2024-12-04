package com.example.myFashionTrunk.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;


public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    @Query("DELETE FROM Category c WHERE c.user.id = :userId ")
    void deleteAllByUserId(@Param("userId") Integer userId);
}
