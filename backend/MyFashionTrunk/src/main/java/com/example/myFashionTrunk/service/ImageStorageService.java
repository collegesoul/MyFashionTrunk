package com.example.myFashionTrunk.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Service class for image storage operations
 * **/
@Service
public class ImageStorageService {
    private final Storage storage;

    /**
     * Constructor for ImageStorageService.
     * @param storage The Google Cloud Storage service.
     */
    public ImageStorageService(Storage storage) {
        this.storage = storage;
    }

    /**
     * Stores an image in the specified bucket and returns the URL to the stored image.
     * @param image The image file to be stored.
     * @param bucketName The name of the bucket where the image will be stored.
     * @param title The title of the image.
     * @param userName The name of the user uploading the image.
     * @return The URL to the stored image.
     * @throws IOException If an error occurs during image storage.
     */
    public String storeImage(MultipartFile image, String bucketName, String title, String userName) throws IOException {
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException(String.format("Bucket %s does not exist", bucketName));
        }
        String imageType = image.getOriginalFilename().split("\\.")[1];

        String filename = String.format("%s/%s_%s_%s.%s", "images" ,LocalDate.now(), userName, title, imageType);

        bucket.create(
                filename,
                image.getBytes()
        );
        return String.format("https://storage.cloud.google.com/%s/%s", bucketName, filename);
    }

    /**
     * Deletes an image from the specified bucket.
     * @param bucketName The name of the bucket where the image is stored.
     * @param filename The URL of the image to be deleted.
     */
    public void deleteImage(String bucketName, String filename) {
        String imageName = filename.split(String.format("%s/", bucketName))[1];
        BlobId blobId = BlobId.of(bucketName, imageName);
        storage.delete(blobId);

    }
}


