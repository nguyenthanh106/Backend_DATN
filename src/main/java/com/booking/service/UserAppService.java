package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.dto.UserLogin;
import com.booking.dto.error.ApiError;
import com.booking.dto.request.RoleAddRequest;
import com.booking.dto.request.UserRequest;
import com.booking.enums.RoleName;
import com.booking.exception.AppException;
import com.booking.jwt.JWTGenerator;
import com.booking.model.Role;
import com.booking.model.UserApp;
import com.booking.repository.UserRepository;
import com.booking.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAppService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    public ResponseEntity<?> create(@Valid @RequestBody UserRequest userRequest) {
        try {
            userRepository.findByEmail(userRequest.getEmail())
                    .ifPresent(user -> {
                        throw new AppException(ErrorMessage.EMAIL_ALREADY_EXIST);
                    });

            userRepository.findByUsername(userRequest.getUsername())
                    .ifPresent(user -> {
                        throw new AppException(ErrorMessage.ACCOUNT_ALREADY_EXIST);
                    });

            UserApp userApp = UserApp.builder()
                    .phone(userRequest.getPhone())
                    .fullname(userRequest.getFullname())
                    .email(userRequest.getEmail())
                    .username(userRequest.getUsername())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .address(userRequest.getAddress())
                    .gender(userRequest.getGender())
                    .imageId(userRequest.getImageId() != null ? userRequest.getImageId() : null)
                    .build();
            Optional<Role> userRole = roleService.findByRoleName(RoleName.USER);
            if (userRole.isEmpty()) {
                userRole = roleService.create(RoleName.USER);
            }
            Set<Role> roles = new HashSet<>();
            roles.add(userRole.get());
            userApp.setRoles(roles);
            userRepository.save(userApp);

            ApiError apiError = new ApiError(HttpStatus.CREATED, "User created successfully!", userApp);
            return ResponseUtil.jsonResponse(apiError, HttpStatus.CREATED);

        } catch (AppException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error creating user, try again!", e.getMessage());
            return ResponseUtil.jsonResponse(apiError, HttpStatus.BAD_REQUEST);
        }

    }

    public UserApp findByUsername(String username) throws AccountNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AccountNotFoundException());
    }

    public UserLogin authenticate(UserLogin userLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getUsername(),
                        userLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String access_token = jwtGenerator.generateToken(authentication);
        return new UserLogin(userLogin.getUsername(), userLogin.getPassword(), access_token);
    }


    public UserApp findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorMessage.ACCOUNT_NOT_FOUND));
    }

    public UserApp addRole(RoleAddRequest roleAddRequest) {
        UserApp userApp = findById(roleAddRequest.getUserId());

        Set<Role> roles = roleAddRequest.getRoleIds().stream().map(roleId -> {
            return roleService.findById(roleId);
        }).collect(Collectors.toSet());
        userApp.setRoles(roles);
        return userApp;
    }
}
