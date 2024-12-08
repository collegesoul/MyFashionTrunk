package com.example.myFashionTrunk.listing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * response object of listing sent to the client
 * **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingResponse {
    private Integer id;
    private String title;
    private String category;
    private String imageUrl;
    private String status;
}
