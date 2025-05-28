package com.project.foodapi.service;

import com.project.foodapi.entity.PlaceOrder;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;

public interface IOrderService
{
  PlaceOrder addPlaceOrder(PlaceOrder request) throws RazorpayException;

  void verifyPayment(Map<String,String> paymentData, String status);

  List<PlaceOrder> getUserOrders();

  void removeOrder(String orderID);

  List<PlaceOrder> getOrdersOfAllUsers();//admin
    void updateOrderStatus(String orderId,String status);



}
