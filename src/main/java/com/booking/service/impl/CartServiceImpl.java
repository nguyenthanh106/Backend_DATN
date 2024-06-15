package com.booking.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.CartItem;
import com.booking.repository.CartRepository;
import com.booking.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartItem> getAllItems() {
        return cartRepository.findAll();
    }

    @Override
    public CartItem getItemById(Long id) {
        Optional<CartItem> optionalCartItem = cartRepository.findById(id);
        return optionalCartItem.orElse(null);
    }

    @Override
    public CartItem addItem(CartItem item) {
        return cartRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
    	cartRepository.deleteById(id);
    }

    @Override
    public CartItem updateItem(Long id, CartItem item) {
        item.setId(id);
        return cartRepository.save(item);
    }
    
    @Override
    public void deleteAllItems() {
        cartRepository.deleteAll();
    }
}