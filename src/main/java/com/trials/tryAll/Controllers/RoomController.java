package com.trials.tryAll.Controllers;

import com.trials.tryAll.Models.Guest;
import com.trials.tryAll.Models.Room;
import com.trials.tryAll.Services.GuestService;
import com.trials.tryAll.Services.RoomService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/rooms")
@Api(tags = "Rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping(value = "/add")
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room){
        Room result = roomService.saveRoom(room);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> result = roomService.getAllRooms();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") long id){
        Room result = roomService.getRoomById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") long id){
        roomService.deleteRoomById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") long id, @Valid @RequestBody Room room){
        Room result = roomService.updateRoom(id, room);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
