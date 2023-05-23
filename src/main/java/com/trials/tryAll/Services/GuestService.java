package com.trials.tryAll.Services;

import com.trials.tryAll.Models.CheckInCheckOutDates;
import com.trials.tryAll.Models.Guest;
import com.trials.tryAll.Models.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GuestService {
    Guest saveGuest(Guest guest);

    List<Guest> getAllGuests();

    Guest getGuestById(long id);

    void deleteGuestById(long id);

    Guest updateGuest(long id, Guest guest);

    Guest checkIn(long guestId, long roomId, CheckInCheckOutDates dates);

    Guest checkOut(long guestId);

    Reservation addReservation(long guestId, long roomId, Reservation reservation);

    void deleteReservationById(long guestId, long reservationId);
}
