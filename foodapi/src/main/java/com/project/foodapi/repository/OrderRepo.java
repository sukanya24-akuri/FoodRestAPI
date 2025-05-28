package com.project.foodapi.repository;

import com.project.foodapi.entity.PlaceOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo  extends MongoRepository<PlaceOrder,String>
{
List<PlaceOrder> findByUserID(String userID);

Optional<PlaceOrder>  findByRazorpayOrderId(String razorpayID);
}
