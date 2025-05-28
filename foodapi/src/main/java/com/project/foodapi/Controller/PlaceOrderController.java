package com.project.foodapi.Controller;

import com.project.foodapi.entity.PlaceOrder;
import com.project.foodapi.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class PlaceOrderController
{
    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<?> orderedPlaces(@RequestBody PlaceOrder request)
    {
try
{
    PlaceOrder order=orderService.addPlaceOrder(request);
    return  new ResponseEntity<>(order,HttpStatus.OK);
}
catch (Exception e)
{
    return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
    }

@PostMapping("/verify")
public void verifypayment(@RequestBody Map<String,String> paymentdata)
{
    orderService.verifyPayment(paymentdata,"Paid");
}

@GetMapping
public ResponseEntity<?> getuserOrders()
{
   try
   {
      List<PlaceOrder>  order=orderService.getUserOrders();
       return  new ResponseEntity<>(order,HttpStatus.OK);
   }
   catch (Exception e)
   {
       return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
   }
}


    @DeleteMapping("/{orderID}")
    public ResponseEntity<String> deleteOrderID(@PathVariable String orderID) {
        try {
            orderService.removeOrder(orderID);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting order: " + e.getMessage());
        }
    }
//admin Panel
   @GetMapping("/all")
    public List<PlaceOrder> getOrdersOfAllUsers()
    {
       return orderService.getOrdersOfAllUsers();
    }
//admin Panel
    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId,@RequestParam String status)
    {
        orderService.updateOrderStatus(orderId,status);
    }
}
