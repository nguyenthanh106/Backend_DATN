package com.booking.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.model.Owner;
import com.booking.model.Room;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
	
}