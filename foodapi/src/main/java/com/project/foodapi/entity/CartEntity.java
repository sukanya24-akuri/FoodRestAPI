package com.project.foodapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Data
@Getter
@Setter
public class CartEntity
{
    @Id
    private String foodId;
    private String userId;
    private Map<String,Integer> items=new HashMap<>();

    public CartEntity(String userId, Map<String, Integer> items) {
        this.userId = userId;
        this.items = items;
    }


}
