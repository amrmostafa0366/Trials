package com.trials.tryAll.Services;

import com.trials.tryAll.Errors.NotFoundException;
import com.trials.tryAll.Models.Reservation;
import com.trials.tryAll.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationServiceImplementation implements ReservationService{

    @Autowired
    ReservationRepository reservationRepository;
    @Override
    public Reservation saveReservation(Reservation reservation) {
       return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        try{
            return reservationRepository.findAll();
        }catch (Exception e){
            throw new NotFoundException("Failed to Retrieve Reservations");
        }

    }

    @Override
    public Reservation getReservationById(long id) {
        try{
            return reservationRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new NotFoundException("No Reservation with that Id");
        }
    }

    @Override
    public Reservation updateReservation(long id, Reservation reservation) {
        return null;
    }
}
