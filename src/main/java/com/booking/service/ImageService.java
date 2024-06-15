package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.dto.request.ImageRequest;
import com.booking.exception.AppException;
import com.booking.model.Image;
import com.booking.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public ResponseEntity<?> create(ImageRequest imageRequest) {
        try {
            Image image = new Image();
            image.setName(imageRequest.getName());
            image.setUrl(imageRequest.getUrl());
            image.setDescription(imageRequest.getDescription());

            imageRepository.save(image);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> update(Long imageId, ImageRequest updatedImage) {
        try {
            Optional<Image> optionalImage = imageRepository.findById(imageId);
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                image.setName(updatedImage.getName());
                image.setUrl(updatedImage.getUrl());
                image.setDescription(updatedImage.getDescription());

                imageRepository.save(image);
                return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully!");
            } else {
                throw new AppException(ErrorMessage.IMAGE_NOT_FOUND);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long imageId) {
        try {
            imageRepository.deleteById(imageId);
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public Image findById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new AppException(ErrorMessage.IMAGE_NOT_FOUND));
    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
