package com.trials.tryAll.Controllers;

import com.trials.tryAll.Models.Reservation;
import com.trials.tryAll.Services.ReservationService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reservations")
@Api(tags = "Reservations")
public class ReservationController {

        @Autowired
        private ReservationService reservationService;

        @PostMapping(value = "/add")
        public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation reservation){
            Reservation result = reservationService.saveReservation(reservation);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }

        @GetMapping(value = "")
        public ResponseEntity<List<Reservation>> getAllReservations(){
            List<Reservation> result = reservationService.getAllReservations();
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
            ReservationService.deleteReservationById(id);
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

