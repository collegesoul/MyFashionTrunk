package com.example.myFashionTrunk.category;

import com.example.myFashionTrunk.listing.ListingRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepo;
    private final ListingRepository listingRepo;

    public CategoryService(CategoryRepository categoryRepo, ListingRepository listingRepo) {
        this.categoryRepo = categoryRepo;
        this.listingRepo = listingRepo;
    }

    public void deleteCategory(Integer categoryId) {
        long listingCount = listingRepo.CountByCategoryId(categoryId);
        if (listingCount > 0) {
            throw new IllegalStateException("Cannot delete category; it has associated listings.");
        }
        categoryRepo.deleteById(categoryId);
    }
}
