package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.dto.request.CategoryRequest;
import com.booking.exception.AppException;
import com.booking.model.Category;
import com.booking.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> create(CategoryRequest categoryRequest) {
        try {
            Category category = new Category();
            category.setName(categoryRequest.getName());

            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> update(Long categoryId, CategoryRequest updatedCategory) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setName(updatedCategory.getName());

                categoryRepository.save(category);
                return ResponseEntity.status(HttpStatus.OK).body("Category updated successfully!");
            } else {
                throw new AppException(ErrorMessage.CATEGORY_NOT_FOUND);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorMessage.CATEGORY_NOT_FOUND));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
