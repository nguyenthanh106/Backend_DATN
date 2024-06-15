package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.exception.AppException;
import com.booking.model.Owner;
import com.booking.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public ResponseEntity<?> create(Owner owner) {
        try {
            ownerRepository.save(owner);
            return ResponseEntity.status(HttpStatus.CREATED).body("Owner created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> update(Long ownerId, Owner owner) {
        try {
            Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
            if (optionalOwner.isPresent()) {
                Owner existingOwner = optionalOwner.get();
                existingOwner.setName(owner.getName());
                existingOwner.setCountry(owner.getCountry());
                existingOwner.setImage(owner.getImage());

                ownerRepository.save(existingOwner);
                return ResponseEntity.status(HttpStatus.OK).body("Owner updated successfully!");
            } else {
                throw new AppException(ErrorMessage.OWNER_NOT_FOUND);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long ownerId) {
        try {
            ownerRepository.deleteById(ownerId);
            return ResponseEntity.status(HttpStatus.OK).body("Owner deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public Owner findById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new AppException(ErrorMessage.OWNER_NOT_FOUND));
    }

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}

