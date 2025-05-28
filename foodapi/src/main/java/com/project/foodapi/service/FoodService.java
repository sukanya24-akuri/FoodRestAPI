package com.project.foodapi.service;

import com.project.foodapi.entity.FoodEntity;
import com.project.foodapi.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoodService implements IFoodService {

    @Autowired
    private S3Client client;
    @Autowired
    private FoodRepo repo;

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String key = UUID.randomUUID().toString() + "." + fileName;
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            PutObjectResponse response = client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            if (response.sdkHttpResponse().isSuccessful()) {
                return "https://" + bucketName + ".s3.amazonaws.com/" + key;

            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file uploaded unsuccessfully");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error-while uploading a file");
        }
    }

    @Override
    public FoodEntity addFood(FoodEntity saveEntity,MultipartFile file)
    {
       String imageurl=uploadFile(file);
       saveEntity.setImageUrl(imageurl);

        return repo.save(saveEntity);

    }



    @Override
    public List<FoodEntity> allFoodDetails() {
        return repo.findAll();
    }


    @Override
    public String deleteByFoodID(String id) {
        Optional<FoodEntity> opt = repo.findById(id);
        if (opt.isPresent()) {
            FoodEntity entity = opt.get();
            String url = entity.getImageUrl();
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            boolean del = deltes3Bucket(fileName);
            if (del) {
                repo.deleteById(id);
                return id + "is deleted";
            } else {
                return "Failed to delete file from S3 for " + id;
            }

        }
        return id + "is not found for deletion";
    }

    @Override
    public boolean deltes3Bucket(String filen) {
        DeleteObjectRequest delreq = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filen)
                .build();
        client.deleteObject(delreq);
        return true;
    }


    @Override
    public FoodEntity getFoodByID(String id) {
        FoodEntity foodEntity = repo.findById(id).orElseThrow();
        return foodEntity;

    }


}
