package com.project.foodapi.service;

import com.project.foodapi.entity.FoodEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFoodService
{
 String uploadFile(MultipartFile file);
 FoodEntity addFood(FoodEntity saveEntity,MultipartFile file);
 public List<FoodEntity> allFoodDetails();
 public String deleteByFoodID(String id);
 boolean deltes3Bucket(String filen);

 FoodEntity getFoodByID(String id);

}
