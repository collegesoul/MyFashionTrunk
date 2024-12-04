package com.example.myFashionTrunk.listing;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/listings")
public class ListingController {
    private final ListingRepository listingRepo;
    private final ListingService listingService;

    public ListingController(ListingRepository listingRepo, ListingService listingService) {
        this.listingRepo = listingRepo;
        this.listingService = listingService;
    }

    @GetMapping("/{userid}")
    List<Listing> getAllListings(@PathVariable Integer userid){
        return null;
    }

    // TODO: MultiPartForm and Listing Image url
    @PostMapping
    void addListing(@Valid @RequestBody Listing listing){
        //
    }

    @PatchMapping("/{id}")
    void updateListing(@PathVariable Integer id, @Valid @RequestBody Listing listing){}

    @DeleteMapping("/{id}")
    void deleteListing(@PathVariable Integer id){}


}
