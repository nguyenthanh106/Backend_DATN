package com.booking.dto.request;


import com.booking.model.Payment;
import com.booking.model.Promotion;
import com.booking.model.Room;
import com.booking.model.UserApp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingOrderRequest {
    private Long orderDetailId;
    private Integer person;
    private Double price;
    private Double totalPrice;
    private Boolean isPaid;
    private UserApp user;
    private Room room;
    private Payment payment;
    private Promotion promotion;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}