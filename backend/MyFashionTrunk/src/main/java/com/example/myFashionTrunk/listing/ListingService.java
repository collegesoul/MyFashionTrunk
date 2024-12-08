package com.example.myFashionTrunk.listing;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.service.ImageDetectionService;
import com.example.myFashionTrunk.service.ImageStorageService;
import com.example.myFashionTrunk.user.User;
import com.example.myFashionTrunk.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ListingService {
    private final ListingRepository listingRepo;
    private final UserRepository userRepo;
    private final ImageDetectionService imgDetection;
    private final ImageStorageService imgStorage;

    private final String bucketName = "my_fashion_trunk_bucket";

    public ListingService(ListingRepository listingRepo,UserRepository userRepo
            ,ImageDetectionService imgDetection, ImageStorageService imgStorage) {
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
        this.imgDetection = imgDetection;
        this.imgStorage = imgStorage;
    }

    public List<ListingResponse> getAllListingsByUserId(Integer userId) {
        List<ListingResponse> userListings = new ArrayList<>();
        for(Listing listing : listingRepo.findAllByUserId(userId)) {
            ListingResponse listingResponse = new ListingResponse();
            listingResponse.setId(listing.getId());
            listingResponse.setTitle(listing.getTitle());
            listingResponse.setImageUrl(listing.getImageUrl());
            listingResponse.setStatus(listing.getStatus());

            Category c = listing.getCategory();
            if (c != null) {
                listingResponse.setCategory(c.getName());
            } else {
                listingResponse.setCategory("no category");
            }
            userListings.add(listingResponse);
        }
        return userListings;
    }

    public ResponseEntity<?> createListing(String title, Integer userId, MultipartFile image) {
        if (title.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Title cannot be empty",
                    "field", "title"
            ));
        } else if (title.length() > 60){
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Title should not be more than 60 characters",
                    "field", "title"
            ));
        }

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "UserId cannot be null"
            ));
        }

        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Image cannot be empty",
                    "field", "file"
            ));
        }

        Optional<User> existingUser = userRepo.findById(userId);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "User not found"
            ));
        }

        User user = existingUser.get();

        // Store Image in bucket and get url to image
        try{
            String imageUrl = imgStorage.storeImage(image, bucketName, title, user.getName());
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
            }).exceptionally(_ -> {
                listing.setStatus("Rejected");
                listingRepo.save(listing);
                return null;
            });
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Image processing failed"
            ));
        }


        return  ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Successfully created listing!"
        ));
    }

    public ResponseEntity<?> deleteListing(Integer id) {
        Optional<Listing> existingListing = listingRepo.findById(id);
        if (existingListing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                    "error", "Listing not found"
            ));
        }
        Listing listing = existingListing.get();
        imgStorage.deleteImage(bucketName,listing.getImageUrl());
        listingRepo.delete(listing);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "message", "Successfully deleted listing!"
        ));
    }
}
