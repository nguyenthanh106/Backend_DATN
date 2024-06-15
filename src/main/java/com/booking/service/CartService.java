package com.booking.service;

import java.util.List;

import com.booking.model.CartItem;

public interface CartService {
    List<CartItem> getAllItems();
    CartItem getItemById(Long id);
    CartItem addItem(CartItem item);
    void deleteItem(Long id);
    CartItem updateItem(Long id, CartItem item);
    void deleteAllItems();
}