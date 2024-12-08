package com.example.myFashionTrunk.category;

import com.example.myFashionTrunk.listing.ListingRepository;
import com.example.myFashionTrunk.user.User;
import com.example.myFashionTrunk.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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

    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        long listingCount = listingRepo.CountByCategoryId(categoryId);
        if (listingCount > 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Cannot delete category; it has associated listings"
            ));
        }
        categoryRepo.deleteById(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public Category addCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());
        Optional<User> existingUser = userRepo.findById(categoryRequest.getUserId());
        if (existingUser.isEmpty()) {
            return null;
        }
        User user = existingUser.get();
        category.setUser(user);
        categoryRepo.save(category);
        return category;
    }
}
