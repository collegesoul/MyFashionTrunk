package com.example.myFashionTrunk.listing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userid}")
    List<ListingResponse> getAllListings(@PathVariable Integer userid){
        return listingService.getAllListingsByUserId(userid);
    }

    @PostMapping
    ResponseEntity<?> addListing(
            @RequestParam("title") String title,
            @RequestParam("userId") Integer userId,
            @RequestParam("image") MultipartFile image
    ){
       return listingService.createListing(title, userId, image);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteListing(@PathVariable Integer id){
        return listingService.deleteListing(id);
    }


}
