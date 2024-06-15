package com.booking.dto.request;

import com.booking.model.Image;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerRequest {
	private String name;
    private String country;
    
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
