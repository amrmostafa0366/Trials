package com.trials.tryAll.Repositories;

import com.trials.tryAll.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Room findByRoomNumber(int roomNumber);
}
