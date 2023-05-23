package com.trials.tryAll.Services;

import com.trials.tryAll.Models.Reservation;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ReservationService {
    Reservation saveReservation(Reservation reservation);

    List<Reservation> getAllReservations();

    Reservation getReservationById(long id);

    Reservation updateReservation(long id, Reservation reservation);

    void deleteReservationById(long id);

    List<Reservation> getGuestReservationsByGuestId(long guestId);

    List<Reservation> getRoomReservationsByRoomId(long roomId);

    List<Reservation> getReservationsByCheckInDate(Date date);

    List<Reservation> getReservationsByCheckOutDate(Date date);
}
