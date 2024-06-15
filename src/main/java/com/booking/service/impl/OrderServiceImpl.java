package com.booking.service.impl;

import com.booking.dto.request.BookingOrderRequest;
import com.booking.model.OrderDetail;
import com.booking.model.Room;
import com.booking.model.UserApp;
import com.booking.repository.OrderDetailRepository;
import com.booking.repository.RoomRepository;
import com.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.BookingOrder;
import com.booking.repository.OrderRepository;
import com.booking.service.OrderService;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public BookingOrder addOrder(BookingOrderRequest bookingOrderRequest) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setPerson(bookingOrderRequest.getPerson());
        orderDetail.setPrice(bookingOrderRequest.getPrice());
        orderDetail.setTotalPrice(bookingOrderRequest.getTotalPrice());
        orderDetail.setIsPaid(bookingOrderRequest.getIsPaid());

        Optional<UserApp> userOptional = userRepository.findById(bookingOrderRequest.getUser().getId());
        if (userOptional.isPresent()) {
            orderDetail.setUser(userOptional.get());
        } else {
            throw new Error("User not found with id: " + bookingOrderRequest.getUser().getId());
        }

        Optional<Room> roomOptional = roomRepository.findById(bookingOrderRequest.getRoom().getId());
        if (roomOptional.isPresent()) {
            orderDetail.setRoom(roomOptional.get());
        } else {
            throw new Error("Room not found with id: " + bookingOrderRequest.getRoom().getId());
        }
        orderDetail.setPayment(bookingOrderRequest.getPayment());
        orderDetail.setPromotion(bookingOrderRequest.getPromotion());

        orderDetail = orderDetailRepository.save(orderDetail);

        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setOrderDetail(orderDetail);
        bookingOrder.setCreateAt(new Timestamp(System.currentTimeMillis()));
        bookingOrder.setUpdateAt(new Timestamp(System.currentTimeMillis()));

        return orderRepository.save(bookingOrder);
    }
}
