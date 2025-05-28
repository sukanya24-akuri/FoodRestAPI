package com.project.foodapi.service;

import com.project.foodapi.entity.PlaceOrder;
import com.project.foodapi.repository.CartRepo;
import com.project.foodapi.repository.OrderRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements  IOrderService
{

    @Autowired
    private OrderRepo repo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private  RegisterImpl registeredUser;
    @Value("${razorpay.key}")
    private  String razorpay_key;
    @Value("${razorpay.secret}")
    private  String razorpay_secretkey;



    @Override
    public PlaceOrder addPlaceOrder(PlaceOrder request) throws RazorpayException {
        PlaceOrder newOrder = repo.save(request);

        //create razorpay order
        RazorpayClient razorpayClient = new RazorpayClient(razorpay_key, razorpay_secretkey);
        JSONObject newRequest = new JSONObject();
        newRequest.put("amount",newOrder.getAmount()*100);
        newRequest.put("currency","INR");
        newRequest.put("payment_capture",1);

        Order razorpayorder=razorpayClient.orders.create(newRequest);
        newOrder.setRazorpayOrderId(razorpayorder.get("id"));
        String loggesUserID = registeredUser.findByUserId();
        newOrder.setUserID(loggesUserID);
      return   repo.save(newOrder);



    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
        String razorpayOrderId=paymentData.get("razorpay_order_id");
        PlaceOrder  existingOrder=repo.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(()->new RuntimeException("order id not found"));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
     existingOrder.setRazorpaymentId(paymentData.get("razorpay_payment_id"));
    repo.save(existingOrder);
    if("paid".equalsIgnoreCase(status))
    {
        cartRepo.deleteByUserId(existingOrder.getUserID());
    }

    }

    @Override
    public List<PlaceOrder> getUserOrders()
    {
        String loggedInID= registeredUser.findByUserId();
        return repo.findByUserID(loggedInID);
    }

    @Override
    public void removeOrder(String orderID)
    {
        repo.deleteById(orderID);
    }

    @Override
    public List<PlaceOrder> getOrdersOfAllUsers()
    {
        List<PlaceOrder> listOrders=repo.findAll();
        return  listOrders;
    }

    @Override
    public void updateOrderStatus(String orderId, String status)
    {
        PlaceOrder entity= repo.findById(orderId).orElseThrow(()->new RuntimeException("order not found"));
        entity.setOrderStatus(status);
        repo.save(entity);
    }


}
