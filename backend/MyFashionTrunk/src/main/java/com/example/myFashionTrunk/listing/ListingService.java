package com.example.myFashionTrunk.listing;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.service.ImageDetectionService;
import com.example.myFashionTrunk.service.ImageStorageService;
import com.example.myFashionTrunk.user.User;
import com.example.myFashionTrunk.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ListingService {
    private final ListingRepository listingRepo;
    private final UserRepository userRepo;
    private final ImageDetectionService imgDetection;
    private final ImageStorageService imgStorage;

    private final String bucketName = "my_fashion_trunk_bucket";

    public ListingService(ListingRepository listingRepo,UserRepository userRepo ,ImageDetectionService imgDetection, ImageStorageService imgStorage) {
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
        this.imgDetection = imgDetection;
        this.imgStorage = imgStorage;
    }

    //TODO: Implement these methods
    public List<Listing> getAllListingsByUserId(Integer userId) {
        return listingRepo.findAllByUserId(userId);
    }

    public void createListing(String title, Integer userId, MultipartFile image) throws IOException, ExecutionException, InterruptedException {
        // Validate Parameters
        if (Objects.equals(title, "")) {
            throw new IllegalArgumentException("title cannot be empty");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("image cannot be null");
        }

        // Store Image in bucket and get url to image
        String imageUrl = imgStorage.storeImage(image, bucketName);

        // Create new Listing, save the values and set initial category and status
        Listing listing = new Listing();
        listing.setTitle(title);
        listing.setUser(user);
        listing.setImageUrl(imageUrl);
        listing.setCategory(null);
        listing.setStatus("Pending");
        listingRepo.save(listing);

        CompletableFuture<Category> categoryFuture = imgDetection.labelImage(image.getBytes());
        categoryFuture.thenAccept(category -> {
            if (category != null) {
                listing.setCategory(category);

                // Set status based on category type
                if(Objects.equals(category.getType(), "Allowed")) {
                    listing.setStatus("Accepted");
                } else if (Objects.equals(category.getType(), "Prohibited")) {
                    listing.setStatus("Rejected");
                }
                // Update the listing with new status and Category
                listingRepo.save(listing);
            } else {
                // set status to rejected if image detection fails or returns null
                listing.setStatus("Rejected");
                listingRepo.save(listing);
            }
        }).exceptionally(ex -> {
            listing.setStatus("Rejected");
            listingRepo.save(listing);
            return null;
        });

    }

    public void deleteListing(Integer id) {
        Listing listing = listingRepo.findById(id).orElseThrow(()->new EntityNotFoundException("listing not found"));
        imgStorage.deleteImage(bucketName,listing.getImageUrl());
        listingRepo.delete(listing);
    }
}
