package com.trials.tryAll.Services;

import com.trials.tryAll.Models.*;
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

    Reservation addReservation(long guestId, long roomId, CheckInCheckOutDates dates);

    void deleteReservationById(long guestId, long reservationId);

    List<Bill> getAllBillsByGuestId(long guestId);

    List<Bill> getAllPayedBillsByGuestId(long guestId);

    List<Bill> getAllUnPayedBillsByGuestId(long guestId);

    Bill payBill(long guestId, long billId, Payment payment);

    Bill makeOrder(long guestId, Order order);
}
