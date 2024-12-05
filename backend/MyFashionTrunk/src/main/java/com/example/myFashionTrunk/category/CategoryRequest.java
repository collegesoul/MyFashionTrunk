package com.example.myFashionTrunk.category;

import com.example.myFashionTrunk.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private Integer id;
    private Integer userId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "type is required")
    private String type;
}
