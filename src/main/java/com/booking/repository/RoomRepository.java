package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long>{
    @Query("SELECT DISTINCT r, ri, i FROM Room r LEFT JOIN FETCH r.roomImages ri LEFT JOIN FETCH ri.image i")
    List<Room> findAllRoomsWithImages();

    @Query(value = "SELECT * FROM Room WHERE location LIKE :location OR person LIKE :person", nativeQuery = true)
    List<Room> findByLocationAndPerson(@Param("location") String location,
        @Param("person") Integer person
    );

}
