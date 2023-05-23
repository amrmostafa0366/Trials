package com.trials.tryAll.Controllers;

import com.trials.tryAll.Models.Reservation;
import com.trials.tryAll.Services.ReservationService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reservations")
@Api(tags = "Reservations")
public class ReservationController {

        @Autowired
        private ReservationService reservationService;
/*
        @PostMapping(value = "/add")
        public ResponseEntity<Reservation> addReservationForExistingGuest(@Valid @RequestBody Reservation reservation){
            Reservation result = reservationService.saveReservation(reservation);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
*/
        @GetMapping(value = "")
        public ResponseEntity<List<Reservation>> getAllReservations(){
            List<Reservation> result = reservationService.getAllReservations();
            return ResponseEntity.ok(result);
        }
        @GetMapping(value = "/guest/{guestId}")
        public ResponseEntity<List<Reservation>> getGuestReservationsByGuestId(@PathVariable long guestId) {
            List<Reservation> result = reservationService.getGuestReservationsByGuestId(guestId);
            return ResponseEntity.ok(result);
        }

        @GetMapping(value = "/room/{roomId}")
        public ResponseEntity<List<Reservation>> getRoomReservationsByRoomId(@PathVariable long roomId) {
            List<Reservation> result = reservationService.getRoomReservationsByRoomId(roomId);
            return ResponseEntity.ok(result);
        }
        @GetMapping(value = "/checkIn-date")
        public ResponseEntity<List<Reservation>> getReservationsByCheckInDate(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date) {
            List<Reservation> result = reservationService.getReservationsByCheckInDate(date);
            return ResponseEntity.ok(result);
        }
    @GetMapping(value = "/checkOut-date")
    public ResponseEntity<List<Reservation>> getReservationsByCheckIOutDate(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyy") Date date){
        List<Reservation> result = reservationService.getReservationsByCheckOutDate(date);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
        public ResponseEntity<Reservation> getReservationById(@PathVariable("id") long id){
            Reservation result = reservationService.getReservationById(id);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping(value = "/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id){
            reservationService.deleteReservationById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        @PutMapping(value = "/{id}")
        public ResponseEntity<Reservation> updateReservation(@PathVariable("id") long id, @Valid @RequestBody Reservation reservation){
            Reservation result = reservationService.updateReservation(id, reservation);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

    }

