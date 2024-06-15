package com.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @Column(length = 512)
    private String url;

    private String description;

    @OneToMany(mappedBy = "image")
    private List<RoomImage> roomImages;
}
