package com.booking.api;

import com.booking.dto.UserLogin;
import com.booking.dto.error.ApiError;
import com.booking.dto.request.RoleAddRequest;
import com.booking.dto.request.UserRequest;
import com.booking.exception.AppException;
import com.booking.model.UserApp;
import com.booking.service.UserAppService;
import com.booking.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AccountApi {
    private final UserAppService userAppService;

    @PostMapping("signup")
    public ResponseEntity<?> createNew(@Valid @RequestBody UserRequest userRequest) {
        try {
            ResponseEntity<?> userRes = userAppService.create(userRequest);
            if (userRes.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userRes.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
            }
        } catch (AppException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid data, try again!", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }
    }

    @GetMapping("get")
    public ResponseEntity<Object> getAccount(
            @RequestParam(name = "username") String username
    ) {
        try {
            UserApp userRes = userAppService.findByUsername(username);
            return ResponseUtil.jsonResponse(userRes, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return ResponseUtil.jsonResponse(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody UserLogin userLogin) {
        try {
            UserLogin result = userAppService.authenticate(userLogin);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }

        } catch (AuthenticationException e) {
            ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid username or password", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }

    @PostMapping("/add-role")

    public ResponseEntity<?> addRole(@RequestBody RoleAddRequest roleAddRequest) {
        return new ResponseEntity<>(userAppService.addRole(roleAddRequest), HttpStatus.OK);
    }

}
