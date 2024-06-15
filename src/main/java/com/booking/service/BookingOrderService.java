package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.dto.request.BookingOrderRequest;
import com.booking.exception.AppException;
import com.booking.model.BookingOrder;
import com.booking.repository.BookingOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingOrderService {

    private final BookingOrderRepository bookingOrderRepository;

    public ResponseEntity<?> create(BookingOrderRequest bookingOrderRequest) {
        try {
            BookingOrder bookingOrder = new BookingOrder();
            // Set properties of bookingOrder from bookingOrderRequest

            bookingOrderRepository.save(bookingOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking order created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> update(Long bookingOrderId, BookingOrderRequest bookingOrderRequest) {
        try {
            Optional<BookingOrder> optionalBookingOrder = bookingOrderRepository.findById(bookingOrderId);
            if (optionalBookingOrder.isPresent()) {
                BookingOrder bookingOrder = optionalBookingOrder.get();
                // Set properties of bookingOrder from bookingOrderRequest

                bookingOrderRepository.save(bookingOrder);
                return ResponseEntity.status(HttpStatus.OK).body("Booking order updated successfully!");
            } else {
                throw new AppException(ErrorMessage.BOOKING_ORDER_NOT_FOUND);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long bookingOrderId) {
        try {
            bookingOrderRepository.deleteById(bookingOrderId);
            return ResponseEntity.status(HttpStatus.OK).body("Booking order deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public BookingOrder findById(Long bookingOrderId) {
        return bookingOrderRepository.findById(bookingOrderId)
                .orElseThrow(() -> new AppException(ErrorMessage.BOOKING_ORDER_NOT_FOUND));
    }

    public List<BookingOrder> findAll() {
        return bookingOrderRepository.findAll();
    }
}

