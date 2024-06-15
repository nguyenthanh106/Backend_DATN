package com.booking.dto.request;

import com.booking.model.Category;
import com.booking.model.Owner;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    private Double price;

    private String location;

    private Integer person;

    private String facility;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
