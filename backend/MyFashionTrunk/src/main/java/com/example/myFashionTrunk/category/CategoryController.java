package com.example.myFashionTrunk.category;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryRepository categoryRepo;
    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepo, CategoryService categoryService) {
        this.categoryRepo = categoryRepo;
        this.categoryService = categoryService;
    }

    @GetMapping
    List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void addCategory(@Valid @RequestBody Category category) {
        categoryRepo.save(category);
    }

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }
}
