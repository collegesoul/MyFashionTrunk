package com.example.myFashionTrunk.services;

import com.google.api.client.util.DateTime;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
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

    public String storeImage(MultipartFile image, String bucketName) throws IOException {
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException(String.format("Bucket %s does not exist", bucketName));
        }

        String filename = String.format("%s_%s", LocalDate.now(), image.getOriginalFilename());

        bucket.create(
                String.valueOf(BlobInfo.newBuilder(bucketName, String.format("images/%s", filename)).build()),
                image.getBytes()
        );

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, filename);
    }


}
