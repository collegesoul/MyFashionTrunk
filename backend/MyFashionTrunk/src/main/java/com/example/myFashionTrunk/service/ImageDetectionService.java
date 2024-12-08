package com.example.myFashionTrunk.service;

import com.example.myFashionTrunk.category.Category;
import com.example.myFashionTrunk.category.CategoryRepository;
import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service class for image detection operations
 * **/
@Service
public class ImageDetectionService {

    private final CategoryRepository categoryRepo;
    private final CloudVisionTemplate cloudVisionTemplate;

    /**
     * Constructor for ImageDetectionService
     * @param categoryRepo repository for category entities
     * @param cloudVisionTemplate the template for interacting with Google Cloud Vision API
     * **/
    public ImageDetectionService(CategoryRepository categoryRepo, CloudVisionTemplate cloudVisionTemplate) {
        this.categoryRepo = categoryRepo;
        this.cloudVisionTemplate = cloudVisionTemplate;
    }

    /**
     * Labels an image using Google Cloud Vision API and matches it to a category
     * @param image image to be labelled in bytes
     * @return CompletableFuture containing a category if there's a match else null
     * @throws IOException if an error occurs during image processing
     * **/
    @Async
    public CompletableFuture<Category> labelImage(byte[] image) throws IOException {
        try {
            Resource imageResource = new ByteArrayResource(image);
            List<Category> categories = categoryRepo.findAll();

            Set<String> labels = cloudVisionTemplate.analyzeImage(imageResource, Feature.Type.LABEL_DETECTION)
                    .getLabelAnnotationsList()
                    .stream()
                    .map(annotation -> StringUtils.trimAllWhitespace(annotation.getDescription().toLowerCase()))
                    .collect(Collectors.toSet());

            Category matchedCategory = categories.stream()
                    .filter(category -> labels
                            .stream()
                            .anyMatch(label->label.contains(category.getName().toLowerCase())))
                    .findFirst()
                    .orElse(null);
            return CompletableFuture.completedFuture(matchedCategory);

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
