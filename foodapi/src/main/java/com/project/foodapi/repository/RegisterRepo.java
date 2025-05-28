package com.project.foodapi.repository;

import com.project.foodapi.entity.RegisterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegisterRepo  extends MongoRepository<RegisterEntity,String>
{
    Optional<RegisterEntity> findByEmail(String email);
}
