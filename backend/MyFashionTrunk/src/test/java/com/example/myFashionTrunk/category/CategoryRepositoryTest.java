package com.example.myFashionTrunk.category;

import com.example.myFashionTrunk.user.User;
import com.example.myFashionTrunk.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private UserRepository userRepo;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .password("Password5")
                .build();
        userRepo.save(user);

        Category category1 = Category.builder()
                .user(user)
                .name("Category 1")
                .type("Allowed")
                .build();

        Category category2 = Category.builder()
                .user(user)
                .name("Category 2")
                .type("Prohibited")
                .build();

        categoryRepo.save(category1);
        categoryRepo.save(category2);
    }

    @Test
    public void whenDeleteAllByUserId_thenAllAssociatedCategoriesAreDeleted() {
        Integer userId = 1;
        categoryRepo.deleteAllByUserId(userId);
        List<Category> categories = categoryRepo.findAll()
                .stream().filter(c -> c.getUser().getId().equals(userId)).toList();
        assertThat(categories).hasSize(0);
    }

    @Test
    public void whenFindByNameIgnoreCase_thenCategoryIsFound() {
        Category category = categoryRepo.findByNameIgnoreCase("category 1");
        assertThat(category).isNotNull();
    }
}
