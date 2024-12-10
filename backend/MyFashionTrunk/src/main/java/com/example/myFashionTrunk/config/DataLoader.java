package com.example.myFashionTrunk.config;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.category.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Component that loads initial data into the database when the application starts
 * **/
@Component
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepo;

    public DataLoader(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    /**
     * Runs data loading
     * @param args commandline arguments
     * **/
    @Override
    public void run(String... args) {
        if (categoryRepo.count() == 0) {
            categoryRepo.saveAll(Arrays.asList(
                    Category.builder().name("Fashion Accessories").type("Allowed").build(),
                    Category.builder().name("Clothes").type("Allowed").build(),
                    Category.builder().name("Footwear").type("Allowed").build(),
                    Category.builder().name("Cosmetics").type("Allowed").build(),
                    Category.builder().name("Children toys").type("Allowed").build(),
                    Category.builder().name("Tech accessories").type("Allowed").build(),
                    Category.builder().name("Pet care products").type("Allowed").build(),
                    Category.builder().name("Food products").type("Prohibited").build(),
                    Category.builder().name("Sports equipment").type("Prohibited").build(),
                    Category.builder().name("Tobacco products").type("Prohibited").build(),
                    Category.builder().name("Cleaning supplies").type("Prohibited").build(),
                    Category.builder().name("Weapon and armoury").type("Prohibited").build(),
                    Category.builder().name("Vehicles and automotive parts").type("Prohibited").build(),
                    Category.builder().name("Natural fur products").type("Prohibited").build()
            ));
        }
    }
}
