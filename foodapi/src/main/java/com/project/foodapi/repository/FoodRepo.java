package com.project.foodapi.repository;

import com.project.foodapi.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepo extends MongoRepository<FoodEntity, String>
{
}

