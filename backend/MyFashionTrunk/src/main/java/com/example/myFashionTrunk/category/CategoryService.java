package com.example.myFashionTrunk.category;

import com.example.myFashionTrunk.listing.ListingRepository;
import com.example.myFashionTrunk.user.User;
import com.example.myFashionTrunk.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;
    private final ListingRepository listingRepo;
    private final UserRepository userRepo;

    public CategoryService(CategoryRepository categoryRepo, ListingRepository listingRepo, UserRepository userRepo) {
        this.categoryRepo = categoryRepo;
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
    }

    public void deleteCategory(Integer categoryId) {
        long listingCount = listingRepo.CountByCategoryId(categoryId);
        if (listingCount > 0) {
            throw new IllegalStateException("Cannot delete category; it has associated listings.");
        }
        categoryRepo.deleteById(categoryId);
    }

    public Category addCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());
        User user = userRepo.findById(categoryRequest.getUserId()).orElseThrow(()->new EntityNotFoundException("user not found"));
        category.setUser(user);
        categoryRepo.save(category);
        return category;
    }
}
