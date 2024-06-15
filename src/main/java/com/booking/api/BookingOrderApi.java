package com.booking.api;

import com.booking.dto.request.BookingOrderRequest;
import com.booking.exception.AppException;
import com.booking.model.BookingOrder;
import com.booking.service.BookingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-orders")
public class BookingOrderApi {
    @Autowired
    private BookingOrderService bookingOrderService;

    @GetMapping
    public ResponseEntity<?> getAllBookingOrders() {
        try {
            List<BookingOrder> bookingOrders = bookingOrderService.findAll();
            if (bookingOrders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bookingOrders);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(bookingOrders);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addBookingOrder(@RequestBody BookingOrderRequest bookingOrderRequest) {
        try {
            ResponseEntity<?> bookingOrderRes = bookingOrderService.create(bookingOrderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingOrderRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookingOrder(@PathVariable Long id) {
        try {
            ResponseEntity<?> bookingOrderRes = bookingOrderService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(bookingOrderRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookingOrder(@PathVariable Long id, @RequestBody BookingOrderRequest bookingOrderRequest) {
        try {
            ResponseEntity<?> bookingOrderRes = bookingOrderService.update(id, bookingOrderRequest);
            if (bookingOrderRes.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(HttpStatus.OK).body(bookingOrderRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bookingOrderRes);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookingOrderById(@PathVariable Long id) {
        try {
            BookingOrder bookingOrder = bookingOrderService.findById(id);
            if (bookingOrder != null) {
                return ResponseEntity.status(HttpStatus.OK).body(bookingOrder);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking order not found");
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
