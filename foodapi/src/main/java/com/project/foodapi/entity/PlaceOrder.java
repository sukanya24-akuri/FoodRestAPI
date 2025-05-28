package com.project.foodapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrder
{
    @Id
    private String id;
    private String userID;
    private String name;
    private String address;
    private  String email;
    private  Long phoneNumber;
    private List<OrderItemsEntity> orderedItems;
    private  double amount;
    private String paymentStatus;
    private String razorpayOrderId;
    private String razorpaySignature;
    private  String orderStatus;
    private  String razorpaymentId;
}