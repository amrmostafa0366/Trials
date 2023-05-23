package com.trials.tryAll.Repositories;

import com.trials.tryAll.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.roomId = :roomId " +
            "AND r.checkOutDate > :checkInDate AND r.checkInDate < :checkOutDate")
    List<Reservation> findBetweenDates(@Param("roomId") long roomId,
                                       @Param("checkInDate") Date checkInDate,
                                       @Param("checkOutDate") Date checkOutDate);
}
