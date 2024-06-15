package com.booking.api;

import java.util.List;

import com.booking.dto.request.RoomRequest;
import com.booking.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booking.model.Room;
import com.booking.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomApi {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> rooms = roomService.findAll();
            if (rooms.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rooms);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(rooms);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addRoom(@RequestBody RoomRequest roomRequest) {
        try {
            ResponseEntity<?> roomRes = roomService.create(roomRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(roomRes);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try {
            ResponseEntity<?> roomRes = roomService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(roomRes);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomRequest roomRequest) {
        try {
            ResponseEntity<?> roomRes = roomService.update(id, roomRequest);
            if (roomRes.getStatusCode().equals(HttpStatus.OK)) {
                return ResponseEntity.status(HttpStatus.OK).body(roomRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(roomRes);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRoomById(@PathVariable Long id) {
        try {
            Room roomRes = roomService.findById(id);
            if (roomRes != null) {
                return ResponseEntity.status(HttpStatus.OK).body(roomRes);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchRoomsByLocationAndPerson(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "person", required = false) Integer person
    ) {
        try {
            List<Room> rooms = roomService.findByLocationAndPerson(location, person);
            if (rooms.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rooms found for the specified location and person count");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(rooms);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
