package com.example.myFashionTrunk.listing;

import com.example.myFashionTrunk.services.ImageDetectionService;
import com.example.myFashionTrunk.services.ImageStorageService;
import org.springframework.stereotype.Service;

@Service
public class ListingService {
    private final ListingRepository listingRepo;
    private final ImageDetectionService imgDetection;
    private final ImageStorageService imgStorage;

    public ListingService(ListingRepository listingRepo, ImageDetectionService imgDetection, ImageStorageService imgStorage) {
        this.listingRepo = listingRepo;
        this.imgDetection = imgDetection;
        this.imgStorage = imgStorage;
    }

    //TODO: Implement these methods

    public void createListing() {}

    public void deleteListing(Integer id) {}

    public void updateListing(Integer id) {}
}
