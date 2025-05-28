package com.project.foodapi.service;

import com.project.foodapi.entity.CartEntity;

import java.util.List;

public interface ICartService
{

     CartEntity addtoCart(String userid);

     CartEntity getCartItems();
   void deleteCartItems();
    CartEntity deleteCartQty(String req);

    public String deleteSingleItemFromCart( String foodIdToDelete);
}
