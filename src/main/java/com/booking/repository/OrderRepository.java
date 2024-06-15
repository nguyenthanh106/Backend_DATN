package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.BookingOrder;

@Repository
public interface OrderRepository extends JpaRepository<BookingOrder, Long> {

}