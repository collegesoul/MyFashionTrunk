package com.example.myFashionTrunk.category;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller for handling category-related operations
 * **/
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryRepository categoryRepo;
    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepo, CategoryService categoryService) {
        this.categoryRepo = categoryRepo;
        this.categoryService = categoryService;
    }

    /**
     * Retrieves all Categories
     * @return a List of Categories
     * **/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    /**
     * Adds a category
     * @param categoryRequest the category details to create
     * @return added category
     * **/
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Category addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    /**
     * Delete category with specified id
     * @param id the id of the category to delete
     * @return ResponseEntity containing an error message or successful delete message
     * **/
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable int id)  {
        return categoryService.deleteCategory(id);
    }
}
