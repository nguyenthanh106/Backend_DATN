package com.booking.api;

import com.booking.dto.request.ImageRequest;
import com.booking.exception.AppException;
import com.booking.model.Image;
import com.booking.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageApi {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAllImages() {
        try {
            List<Image> images = imageService.findAll();
            if (images.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(images);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(images);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addImage(@RequestBody ImageRequest imageRequest) {
        try {
            ResponseEntity<?> imageRes = imageService.create(imageRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        try {
            ResponseEntity<?> imageRes = imageService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(imageRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImage(@PathVariable Long id, @RequestBody ImageRequest updatedImage) {
        try {
            ResponseEntity<?> imageRes = imageService.update(id, updatedImage);
            if (imageRes.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(HttpStatus.OK).body(imageRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(imageRes);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findImageById(@PathVariable Long id) {
        try {
            Image imageRes = imageService.findById(id);
            if (imageRes != null) {
                return ResponseEntity.status(HttpStatus.OK).body(imageRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
