package com.example.myFashionTrunk.category;

import com.example.myFashionTrunk.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity represent a category
 * **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    /**
     * The name of the Category
     * This field is required and should not contain a null or an empty character ("")
     * **/
    @NotBlank(message = "name is required")
    private String name;

    /**
     * The type of Category
     * Values include "Allowed" and "Prohibited"
     * This field is required and should not contain a null or an empty character ("")
     * **/
    @NotBlank(message = "type is required")
    private String type;
}
