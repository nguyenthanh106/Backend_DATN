package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
}