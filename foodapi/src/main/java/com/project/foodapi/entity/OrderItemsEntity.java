package com.project.foodapi.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
@Data
public class OrderItemsEntity
{

    private String id;
    private String name;
    private String category;
    private double price;
    private String imageUrl;
    private int quantity;
    private String description;
}
