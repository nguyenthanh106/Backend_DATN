package com.booking.contants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage {

    private String message;

    public static final String ACCOUNT_ALREADY_EXIST = "Tài khoản đã tồn tại hoặc username đã được sử dụng!";
    public static final String ACCOUNT_NOT_FOUND = "Tài khoản không tồn tại!";
    public static final String EMAIL_ALREADY_EXIST = "Email đã tồn tại!";
    public static final String ROLE_NOT_FOUND = "Role không tồn tại!";
    public static final String ROOM_NOT_FOUND = "Danh sách trống";
    public static final String OWNER_NOT_FOUND = "Danh sách trống";
    public static final String IMAGE_NOT_FOUND = "Không có hình ảnh";
    public static final String CATEGORY_NOT_FOUND = "không có loại đó trong danh sách";
    public static final String BOOKING_ORDER_NOT_FOUND = "không có hóa đơn";
}
