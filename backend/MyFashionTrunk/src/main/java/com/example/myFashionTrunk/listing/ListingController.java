package com.example.myFashionTrunk.listing;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/{userid}")
    List<ListingResponse> getAllListings(@PathVariable Integer userid){
        return listingService.getAllListingsByUserId(userid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void addListing(
            @RequestParam("title") String title,
            @RequestParam("userId") Integer userId,
            @RequestParam("image") MultipartFile image
    ) throws IOException, ExecutionException, InterruptedException {
        listingService.createListing(title, userId, image);
    }

    @DeleteMapping("/{id}")
    void deleteListing(@PathVariable Integer id){
        listingService.deleteListing(id);
    }


}
