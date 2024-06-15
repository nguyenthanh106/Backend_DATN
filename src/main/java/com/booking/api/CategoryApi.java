package com.booking.api;

import com.booking.dto.request.CategoryRequest;
import com.booking.exception.AppException;
import com.booking.model.Category;
import com.booking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.findAll();
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categories);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(categories);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            ResponseEntity<?> categoryRes = categoryService.create(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            ResponseEntity<?> categoryRes = categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(categoryRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        try {
            ResponseEntity<?> categoryRes = categoryService.update(id, categoryRequest);
            if (categoryRes.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(HttpStatus.OK).body(categoryRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryRes);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.findById(id);
            if (category != null) {
                return ResponseEntity.status(HttpStatus.OK).body(category);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

