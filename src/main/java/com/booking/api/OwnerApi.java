package com.booking.api;

import com.booking.dto.request.OwnerRequest;
import com.booking.exception.AppException;
import com.booking.model.Owner;
import com.booking.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerApi {
    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public ResponseEntity<?> getAllOwners() {
        try {
            List<Owner> owners = ownerService.findAll();
            if (owners.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(owners);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(owners);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addOwner(@RequestBody OwnerRequest ownerRequest) {
        try {
            Owner owner = new Owner();
            owner.setName(ownerRequest.getName());
            owner.setCountry(ownerRequest.getCountry());
            // set other fields if needed
            
            ResponseEntity<?> ownerRes = ownerService.create(owner);
            return ResponseEntity.status(HttpStatus.CREATED).body(ownerRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        try {
            ResponseEntity<?> ownerRes = ownerService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(ownerRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody OwnerRequest ownerRequest) {
        try {
            Owner owner = new Owner();
            owner.setName(ownerRequest.getName());
            owner.setCountry(ownerRequest.getCountry());
            // set other fields if needed
            
            ResponseEntity<?> ownerRes = ownerService.update(id, owner);
            if (ownerRes.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(HttpStatus.OK).body(ownerRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ownerRes);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOwnerById(@PathVariable Long id) {
        try {
            Owner owner = ownerService.findById(id);
            if (owner != null) {
                return ResponseEntity.status(HttpStatus.OK).body(owner);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found");
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

