package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.dto.request.RoomRequest;
import com.booking.exception.AppException;
import com.booking.model.Room;
import com.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public ResponseEntity<?> create(RoomRequest roomRequest) {
        try {
            Room room = new Room();
            room.setPrice(roomRequest.getPrice());
            room.setLocation(roomRequest.getLocation());
            room.setPerson(roomRequest.getPerson());
            room.setFacility(roomRequest.getFacility());
            room.setIsActive(true);
            room.setOwner(roomRequest.getOwner());
            room.setCategory(roomRequest.getCategory());

            roomRepository.save(room);
            return ResponseEntity.status(HttpStatus.CREATED).body("Room created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> update(Long roomId, RoomRequest roomRequest) {
        try {
            Optional<Room> optionalRoom = roomRepository.findById(roomId);
            if (optionalRoom.isPresent()) {
                Room room = optionalRoom.get();
                room.setPrice(roomRequest.getPrice());
                room.setLocation(roomRequest.getLocation());
                room.setPerson(roomRequest.getPerson());
                room.setFacility(roomRequest.getFacility());
                room.setOwner(roomRequest.getOwner());
                room.setCategory(roomRequest.getCategory());

                roomRepository.save(room);
                return ResponseEntity.status(HttpStatus.OK).body("Room updated successfully!");
            } else {
                throw new AppException(ErrorMessage.ROOM_NOT_FOUND);
            }
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long roomId) {
        try {
            roomRepository.deleteById(roomId);
            return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorMessage.ROOM_NOT_FOUND));
    }

    public List<Room> findByLocationAndPerson(String location, Integer person) {
        return roomRepository.findByLocationAndPerson(location, person);
    }

    public List<Room> findAll() {
        return roomRepository.findAllRoomsWithImages();
    }

}