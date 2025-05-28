package com.project.foodapi.repository;

import com.project.foodapi.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepo extends MongoRepository<FoodEntity, String>
{
}


//kk okasre payment cheska chudu cart clear avuthdu haa
//kk ipudu akda delete avutalduu kadaa haa
//ade akada integrate chesvaaabutton
//vunvaaha unaavedio akkda adramkala