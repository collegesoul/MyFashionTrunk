package com.example.myFashionTrunk.listing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * controller for handling listing-related operations
 * **/
@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    /**
     * Retrieves all listings associated to a specified user
     * @param userId the user id of specified user
     * @return List of ListingResponse Object
     * **/
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    List<ListingResponse> getAllListings(@PathVariable Integer userId){
        return listingService.getAllListingsByUserId(userId);
    }

    /**
     * Create a new Listing
     * @param title the title of the listing
     * @param userId the id of user creating the listing
     * @param image the image file of the listing
     * @return ResponseEntity containing an error message or a successful create message
     * **/
    @PostMapping
    ResponseEntity<?> addListing(
            @RequestParam("title") String title,
            @RequestParam("userId") Integer userId,
            @RequestParam("image") MultipartFile image
    ){
       return listingService.createListing(title, userId, image);
    }

    /**
     * Delete a listing with specified id
     * @param id the id of the listing to delete
     * @return ResponseEntity containing an error message or a successful delete message
     * **/
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteListing(@PathVariable Integer id){
        return listingService.deleteListing(id);
    }


}
