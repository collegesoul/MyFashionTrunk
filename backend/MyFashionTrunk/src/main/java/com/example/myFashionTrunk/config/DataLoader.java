package com.example.myFashionTrunk.config;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.category.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepo;

    public DataLoader(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        if (categoryRepo.count() == 0) {
            categoryRepo.saveAll(Arrays.asList(
                    new Category(null, null, "Fashion accessories", "Allowed"),
                    new Category(null, null, "Clothes", "Allowed"),
                    new Category(null, null, "Footwear", "Allowed"),
                    new Category(null, null, "Cosmetics", "Allowed"),
                    new Category(null, null, "Children toys", "Allowed"),
                    new Category(null, null, "Tech accessories", "Allowed"),
                    new Category(null, null, "Pet care products", "Allowed"),
                    new Category(null, null, "Food products", "Prohibited"),
                    new Category(null, null, "Sports equipment", "Prohibited"),
                    new Category(null, null, "Tobacco products", "Prohibited"),
                    new Category(null, null, "Cleaning supplies", "Prohibited"),
                    new Category(null, null, "Weapon and armoury", "Prohibited"),
                    new Category(null, null, "Vehicles and automotive parts", "Prohibited"),
                    new Category(null, null, "Natural fur products", "Prohibited")
            ));
        }
    }
}
