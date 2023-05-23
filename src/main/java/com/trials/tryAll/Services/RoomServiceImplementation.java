package com.trials.tryAll.Services;

import com.trials.tryAll.Errors.ConflictException;
import com.trials.tryAll.Errors.NotFoundException;
import com.trials.tryAll.Models.Room;
import com.trials.tryAll.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomServiceImplementation implements RoomService{

    @Autowired
    private RoomRepository roomRepository;
    @Override
    public Room saveRoom(Room room) {
        if(roomRepository.findByRoomNumber(room.getRoomNumber())!=null){
            throw new ConflictException("This Room Is Already Exist");
        }
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(long id) {
        try{
            return roomRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new NotFoundException("No Room With That ID");
        }
    }

    @Override
    public void deleteRoomById(long id) {
        if(roomRepository.existsById(id)){
            roomRepository.deleteById(id);
        }else{
            throw new NotFoundException("No Record with that id");
        }

    }

    @Override
    public Room updateRoom(long id, Room room) {
        Room roomDB;
        try{
            roomDB = roomRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new NotFoundException("No Record with that ID");
        }if(roomRepository.findByRoomNumber(room.getRoomNumber())!=null){
            throw new ConflictException("This Room Already Exist");
        }
        roomDB.setRoomNumber(room.getRoomNumber());
        return roomRepository.save(roomDB);
    }
}
