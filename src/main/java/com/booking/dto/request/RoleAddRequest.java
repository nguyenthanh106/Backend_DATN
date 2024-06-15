package com.booking.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleAddRequest {
    private List<Integer> roleIds;
    private Integer userId;
}
