package com.booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String phone;
    private String fullname;
    private String email;
    private String username;
    private String password;
    private String address;
    private String gender;
    private Integer imageId;
}
