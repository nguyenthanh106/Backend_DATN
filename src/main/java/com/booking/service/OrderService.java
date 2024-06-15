package com.booking.service;

import java.util.List;

import com.booking.dto.request.BookingOrderRequest;
import com.booking.model.BookingOrder;
import com.booking.model.OrderDetail;

public interface OrderService {

    BookingOrder addOrder(BookingOrderRequest bookingOrderRequest);
}