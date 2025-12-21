package com.foodapp.backend.order;

import com.foodapp.backend.MonAn.MonAn;
import com.foodapp.backend.Response.APIResponse;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public ResponseEntity<APIResponse<List<Order>>> getAllOrder(){
        List<Order> allOrder= orderService.getAllOrder();

        APIResponse<List<Order>> response = new APIResponse<>(
                "success",
                200,
                "Fetch success",
                allOrder
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/orderid/{orderid}")
    public ResponseEntity<APIResponse<Order>> getOrderByid(@PathVariable("orderid") Integer orderid){
        try{
            return ResponseEntity.ok(new APIResponse<Order>(
                    "success",
                    200,
                    "Fetch success",
                    orderService.getOrderByid(orderid)
            ));
        }

        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            ));
        }
    }

    @GetMapping(path = "/userid/{userid}")
    public ResponseEntity<APIResponse<List<Order>>> getAllOrderByUserid(@PathVariable("userid") Integer userid){
        try{
            return ResponseEntity.ok(new APIResponse<List<Order>>(
                    "success",
                    200,
                    "Fetch success",
                    orderService.getAllOrderByUserid(userid)
            ));
        }

        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            ));
        }
    }



    @PostMapping
    public ResponseEntity<APIResponse<Order>> addOrder(@RequestBody Order order){
        try{
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    200,
                    "Order added success",
                    orderService.addOrder(order)
            ));
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    404,
                    e.getMessage(),
                    null
            ));
        }
    }


        @PutMapping(path = "/update-order-status/{orderid}")
        public ResponseEntity<APIResponse<Void>> updateTrangThaiDonHang(@PathVariable("orderid") Integer orderid,
                                                             @RequestParam String status){
            try {
                orderService.updateTrangThaiDonHang(orderid, status);

                APIResponse<Void> response = new APIResponse<>(
                        "success",
                        200,
                        "Update success",
                        null
                );

                return ResponseEntity.ok(response);
            }
            catch (IllegalStateException e) {

                APIResponse<Void> response = new APIResponse<>(
                        "Update failed",
                        404,
                        e.getMessage(),
                        null
                );

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }

        @PutMapping(path = "/update-payment-status/{orderid}")
        public ResponseEntity<APIResponse<Void>> updateTrangThaiThanhToan(@PathVariable("orderid") Integer orderid,
                                                                        @RequestParam String status){
            try {
                orderService.updateTrangThaiThanhToan(orderid, status);

                APIResponse<Void> response = new APIResponse<>(
                        "success",
                        200,
                        "Update success",
                        null
                );

                return ResponseEntity.ok(response);
            }
            catch (IllegalStateException e) {

                APIResponse<Void> response = new APIResponse<>(
                        "Update failed",
                        404,
                        e.getMessage(),
                        null
                );

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }

}
