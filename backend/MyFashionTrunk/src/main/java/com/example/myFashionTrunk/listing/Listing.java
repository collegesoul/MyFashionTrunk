package com.example.myFashionTrunk.listing;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "title is required")
    @Size(min = 3, max = 60, message = "title should be between 3 - 60 characters long")
    private String title;

    @NotBlank(message = "image_url is required")
    private String imageUrl;

    @NotBlank(message = "status is required")
    private String status;
}
