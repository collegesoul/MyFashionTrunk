package com.example.myFashionTrunk.listing;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.category.CategoryRepository;
import com.example.myFashionTrunk.user.User;
import com.example.myFashionTrunk.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class ListingRepositoryTest {
    @Autowired
    private ListingRepository listingRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private UserRepository userRepo;

    private Category category1;


    @BeforeEach
    public void setUp() {
        User user1 = User.builder()
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .password("Password5")
                .build();
        User user2 = User.builder()
                .name("Mary")
                .surname("Doe")
                .email("mary@example.com")
                .password("Password6")
                .build();

        userRepo.save(user1);
        userRepo.save(user2);

        category1 = Category.builder()
                .name("Category 1")
                .type("Allowed")
                .build();
        Category category2 = Category.builder()
                .name("Category 2")
                .type("Allowed")
                .build();

        categoryRepo.save(category1);
        categoryRepo.save(category2);

        Listing listing1 = Listing.builder()
                .title("listing One")
                .user(user1)
                .category(category1)
                .imageUrl("image1")
                .status("Allowed")
                .build();

        Listing listing2 = Listing.builder()
                .title("listing Two")
                .user(user2)
                .category(category1)
                .imageUrl("image2")
                .status("Allowed")
                .build();

        Listing listing3 = Listing.builder()
                .title("listing Two")
                .user(user1)
                .category(category2)
                .imageUrl("image3")
                .status("Allowed")
                .build();

        listingRepo.save(listing1);
        listingRepo.save(listing2);
        listingRepo.save(listing3);
    }

    @Test
    public void whenCountByCategoryId_thenReturnCorrectCount(){
        long count = listingRepo.CountByCategoryId(category1.getId());
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void whenFindAllByUserId_thenReturnListOfListings(){
        List<Listing> listings = listingRepo.findAllByUserId(1);
        assertThat(listings).isNotEmpty();
        assertThat(listings.size()).isEqualTo(2);
    }

    @Test
    public void whenDeleteAllByUserId_thenListingsShouldBeDeleted(){
        listingRepo.deleteAllByUserId(1);
        List<Listing> listings = listingRepo.findAllByUserId(1);
        assertThat(listings).isEmpty();
    }
}
