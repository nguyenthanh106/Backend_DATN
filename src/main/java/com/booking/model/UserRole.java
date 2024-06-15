package com.booking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private Role roleId;
    @NaturalId
    @Column(length = 60)
    private UserApp userId;
}
