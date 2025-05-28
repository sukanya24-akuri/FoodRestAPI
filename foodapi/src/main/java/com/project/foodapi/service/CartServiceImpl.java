package com.project.foodapi.service;

import com.project.foodapi.entity.CartEntity;
import com.project.foodapi.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class CartServiceImpl  implements  ICartService
{
    @Autowired
    private CartRepo repo;
    @Autowired
    private RegisterImpl registerUser;

    @Override
    public CartEntity addtoCart(String foodId) {
        String loggesUserID = registerUser.findByUserId();//find user id
        Optional<CartEntity> optCart = repo.findByUserId(loggesUserID);//check cart empty or not
        CartEntity cart = optCart.orElseGet(() -> new CartEntity(loggesUserID, new HashMap<>()));//if the cart is empty it will create new cart
        Map<String, Integer> cartItems = cart.getItems();//get food items
        cartItems.put(foodId, cartItems.getOrDefault(foodId, 0) + 1);//add items
        cart.setItems(cartItems);//set items
       return repo.save(cart);
    }

    @Override
    public CartEntity getCartItems()
    {
        String loggesUserID = registerUser.findByUserId();
       return repo.findByUserId(loggesUserID).orElse(new CartEntity(loggesUserID,new HashMap<>()));
    }

    @Override
    public void  deleteCartItems() {
        String loggesUserID = registerUser.findByUserId();
        repo.deleteByUserId(loggesUserID);
    }

    @Override
    public CartEntity deleteCartQty(String foodID)
    {
        String loggesUserID = registerUser.findByUserId();
        CartEntity cartEntity = repo.findByUserId(loggesUserID).orElseThrow(() -> new RuntimeException("cart not found"));
        Map<String, Integer> cartitems = cartEntity.getItems();
        if (cartitems.containsKey(foodID))
        {
            int crtQty = cartitems.get(foodID);
            if (crtQty > 0)
            {
                cartitems.put(foodID, crtQty - 1);
            } else
            {
                cartitems.remove(foodID);
            }
        }
        return repo.save(cartEntity);


    }
    @Override
    public String deleteSingleItemFromCart(String foodIdToDelete)
    {
        String loggesUserID = registerUser.findByUserId();
        CartEntity cartEntity = repo.findByUserId(loggesUserID)
                .orElseThrow(() -> new RuntimeException("cart is  not  found"));

        Map<String, Integer> items = cartEntity.getItems();

        if (items.containsKey(foodIdToDelete)) {
            items.remove(foodIdToDelete);
            repo.save(cartEntity);
            return "Deleted food item " + foodIdToDelete;
        } else {
            throw new NoSuchElementException("Item with foodId: " + foodIdToDelete + " not found in cart");
        }
    }


}
