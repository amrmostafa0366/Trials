package com.trials.tryAll.Services;

import com.trials.tryAll.Errors.NotFoundException;
import com.trials.tryAll.Models.Reservation;
import com.trials.tryAll.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationServiceImplementation implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        try {
            return reservationRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("Failed to Retrieve Reservations");
        }

    }

    @Override
    public Reservation getReservationById(long id) {
        try {
            return reservationRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("No Reservation with that Id");
        }
    }

    @Override
    public Reservation updateReservation(long id, Reservation reservation) {
        return null;
    }

    @Override
    public void deleteReservationById(long id) {

    }

    @Override
    public List<Reservation> getGuestReservationsByGuestId(long guestId) {
        try {
            return reservationRepository.findByGuestGuestId(guestId);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Couldn't Retrieve Guest's Reservations");
        }
    }

    @Override
    public List<Reservation> getRoomReservationsByRoomId(long roomId) {
        try {
            return reservationRepository.findByRoomRoomId(roomId);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Couldn't Retrieve Room's Reservations");
        }
    }

    @Override
    public List<Reservation> getReservationsByCheckInDate(Date date) {
        try {
            return reservationRepository.findByCheckInDate(date);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Couldn't Retrieve Room's Reservations");
        }
    }

    @Override
    public List<Reservation> getReservationsByCheckOutDate(Date date) {
        try {
            return reservationRepository.findByCheckOutDate(date);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Couldn't Retrieve Room's Reservations");
        }
    }
}
