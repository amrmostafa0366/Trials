package com.trials.tryAll.Services;

import com.trials.tryAll.Models.Guest;
import com.trials.tryAll.Models.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    Room saveRoom(Room room);

    List<Room> getAllRooms();

    Room getRoomById(long id);

    void deleteRoomById(long id);

    Room updateRoom(long id, Room room);
}
