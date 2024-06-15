package com.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roomImage")
public class RoomImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;
    
    @ManyToOne
    @JoinColumn(name = "image_id")
    @JsonIgnore
    private Image image;

    public String getImageUrl() {
        return this.image.getUrl();
    }

    public String getImageName() {
        return this.image.getName();
    }

    public String getDescription() {
        return this.image.getDescription();
    }
}
