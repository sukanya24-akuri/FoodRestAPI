package com.project.foodapi.Controller;

import com.project.foodapi.entity.CartEntity;
import com.project.foodapi.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController
{
    @Autowired
private ICartService service;

    @PostMapping
     public ResponseEntity<?> addtoCartItems(@RequestBody CartEntity request)
     {
         try
         {
      CartEntity updatedCart=service.addtoCart(request.getFoodId());
      if(updatedCart==null)
      {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"food id not found");
      }
         return new ResponseEntity<>(updatedCart, HttpStatus.OK);
         }
         catch (Exception e)
         {
             return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

@GetMapping
     public ResponseEntity<?> getCartItems()
      {
          try {
              CartEntity cart = service.getCartItems();
              return new ResponseEntity<>(cart,HttpStatus.OK);
          }
          catch (Exception e)
          {
              return  new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }
     }



    @DeleteMapping()
    public ResponseEntity<?> removeCartItems()
    {
        try {
            service.deleteCartItems();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@PostMapping("/remove")
    public ResponseEntity<?> deleteItemsQty(@RequestBody CartEntity request)
    {
        try
        {
           CartEntity cartdelete=service.deleteCartQty(request.getFoodId());
            if(cartdelete==null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"food id not found");
            }
            return new ResponseEntity<>(cartdelete, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/item")
    public ResponseEntity<?> deleteCartItem(@RequestParam String foodId) {


        try {

            String result = service.deleteSingleItemFromCart( foodId);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
