package com.trials.tryAll.Services;

import com.trials.tryAll.Errors.ApiBaseException;
import com.trials.tryAll.Errors.ConflictException;
import com.trials.tryAll.Errors.NotFoundException;
import com.trials.tryAll.Models.Guest;
import com.trials.tryAll.Models.Reservation;
import com.trials.tryAll.Models.Room;
import com.trials.tryAll.Repositories.GuestRepository;
import com.trials.tryAll.Repositories.ReservationRepository;
import com.trials.tryAll.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class GuestServiceImplementation implements GuestService{

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Guest saveGuest(Guest guest) {
          if(guestRepository.findByGuestName(guest.getGuestName())!=null){
              throw new ConflictException("this user is already exist");
          }
            return guestRepository.save(guest);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuestById(long id) {
        try{
            return guestRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new NotFoundException("No Record with that id");
        }
    }

    @Override
    public void deleteGuestById(long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
        }else{
            throw new NotFoundException("No Record with that id");
        }

    }

    @Override
    public Guest updateGuest(long id, Guest guest) {
        Guest guestDB;
        try {
            guestDB = guestRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("No Record With That ID");
        }
        if (guestRepository.findByGuestName(guest.getGuestName()) != null) {
            throw new ConflictException("this Guest Name is already exist");
        }
        guestDB.setGuestName(guest.getGuestName());
        return guestRepository.save(guestDB);
    }

    @Override
    public Guest checkIn(long guestId, long roomId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        if(room.getGuest() != null){
            throw new ConflictException("Room Is Not Available");
        }else if(guest.getRoom() != null){
            throw new ConflictException("Guest Is Already CheckedIn");
        }
        guest.setRoom(room);
        room.setGuest(guest);
        roomRepository.save(room);
        return guestRepository.save(guest);
    }

    @Override
    public Guest checkOut(long guestId) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(()-> new NotFoundException("Guest Not Found"));
        if(guest.getRoom()!=null){
            Room room = roomRepository.findById(guest.getRoom().getRoomId()).get();
            guest.setRoom(null);
            room.setGuest(null);
            roomRepository.save(room);
            return guestRepository.save(guest);
        }
        throw new NotFoundException("Guest Have No Room");
    }

    @Override
    public Reservation addReservation(long guestId, long roomId,Reservation reservation) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found"));


        Date checkInDate = reservation.getCheckInDate();
        Date checkOutDate = reservation.getCheckOutDate();

        // Check if there are any existing reservations between the given dates
        List<Reservation> existingReservations = reservationRepository.findBetweenDates(roomId, checkInDate, checkOutDate);
        if (!existingReservations.isEmpty()) {
            throw new ConflictException("There are existing reservations between the specified dates");
        }
        reservation.setGuest(guest);
        reservation.setRoom(room);
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservationById(long guestId, long reservationId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new NotFoundException("Guest not found"));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (reservation.getGuest().getGuestId() != guest.getGuestId()) {
            throw new ConflictException("Guest is not authorized to delete this reservation");
        }

        Room room = reservation.getRoom();
        guest.getReservations().remove(reservation);
        room.getReservations().remove(reservation);

        reservationRepository.deleteById(reservationId);
    }


    //CRUD


}
