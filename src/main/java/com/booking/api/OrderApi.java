package com.booking.api;

import java.util.List;

import com.booking.dto.request.BookingOrderRequest;
import com.booking.model.BookingOrder;
import com.booking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody BookingOrderRequest bookingOrderRequest) {
        try {
            BookingOrder bookingOrder = orderService.addOrder(bookingOrderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding order: " + e.getMessage());
        }
    }


}
