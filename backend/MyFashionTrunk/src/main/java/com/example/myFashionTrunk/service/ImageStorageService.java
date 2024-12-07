package com.example.myFashionTrunk.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class ImageStorageService {
    private final Storage storage;

    public ImageStorageService(Storage storage) {
        this.storage = storage;
    }

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

    public void deleteImage(String bucketName, String filename) {
        String imageName = filename.split(String.format("%s/", bucketName))[1];
        BlobId blobId = BlobId.of(bucketName, imageName);
        storage.delete(blobId);

    }


}
