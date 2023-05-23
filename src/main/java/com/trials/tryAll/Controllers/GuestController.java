package com.trials.tryAll.Controllers;

import com.trials.tryAll.Models.CheckInCheckOutDates;
import com.trials.tryAll.Models.Guest;
import com.trials.tryAll.Models.Reservation;
import com.trials.tryAll.Services.GuestService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Api(tags = "Guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping(value = "/add")
    public ResponseEntity<Guest> addGuest(@Valid @RequestBody Guest guest){
        Guest result = guestService.saveGuest(guest);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Guest>> getAllGuests(){
        List<Guest> result = guestService.getAllGuests();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable("id") long id){
        Guest result = guestService.getGuestById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuestById(@PathVariable("id") long id){
         guestService.deleteGuestById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable("id") long id, @Valid @RequestBody Guest guest){
        Guest result = guestService.updateGuest(id, guest);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/checkIn/{guestId}/{roomId}")
    public ResponseEntity<Guest> checkIn(@PathVariable (value = "guestId") long guestId,
                                         @PathVariable (value = "roomId") long roomId,
                                         @Valid @RequestBody CheckInCheckOutDates dates
                                         ){
        Guest result = guestService.checkIn(guestId,roomId,dates);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PostMapping(value = "/checkOut/{guestId}")
    public ResponseEntity<Guest> checkOut(@PathVariable (value = "guestId") long guestId){
        Guest result = guestService.checkOut(guestId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value = "/reservation/{guestId}/{roomId}")
    public ResponseEntity<Reservation> addReservation(@PathVariable (value = "guestId") long guestId,
                                                      @PathVariable (value = "roomId") long roomId,
                                                      @Valid @RequestBody CheckInCheckOutDates dates){
        Reservation result = guestService.addReservation(guestId,roomId,dates);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/reservation/{guestId}/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable (value = "guestId") long guestId, @PathVariable (value = "reservationId") long reservationId){
        guestService.deleteReservationById(guestId,reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
