package com.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.contants.ErrorMessage;
import com.booking.dto.request.UserRequest;
import com.booking.exception.AppException;
import com.booking.model.UserApp;
import com.booking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> updateUser(Integer userId, UserRequest userRequest) {
        try {
            Optional<UserApp> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                UserApp user = optionalUser.get();
                user.setUsername(userRequest.getUsername());
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                user.setFullname(userRequest.getFullname());
                user.setPhone(userRequest.getPhone());
                user.setEmail(userRequest.getEmail());
                user.setAddress(userRequest.getAddress());
                user.setImageId(userRequest.getImageId());
                
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("User updated successfully!");
            } else {
                throw new AppException(ErrorMessage.ACCOUNT_NOT_FOUND);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteUser(Integer userId) {
        try {
            userRepository.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public UserApp getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorMessage.ACCOUNT_NOT_FOUND));
    }

    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }
}
