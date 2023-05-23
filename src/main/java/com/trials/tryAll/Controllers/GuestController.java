package com.trials.tryAll.Controllers;

import com.trials.tryAll.Models.*;
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

    @GetMapping(value = "/{guestId}/bills")
    public ResponseEntity<List<Bill>> getGuestBills (@PathVariable (value = "guestId") long guestId){
        List<Bill> result = guestService.getAllBillsByGuestId(guestId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping(value = "/{guestId}/payedBills")
    public ResponseEntity<List<Bill>> getGuestPayedBills (@PathVariable (value = "guestId") long guestId){
        List<Bill> result = guestService.getAllPayedBillsByGuestId(guestId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping(value = "/{guestId}/unPayedBills")
    public ResponseEntity<List<Bill>> getGuestUnPayedBills (@PathVariable (value = "guestId") long guestId){
        List<Bill> result = guestService.getAllUnPayedBillsByGuestId(guestId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping(value = "/{guestId}/pay/{billId}")
    public ResponseEntity<Bill> payBill (@PathVariable (value = "guestId") long guestId,
                                               @PathVariable (value = "billId") long billId,
                                               @Valid @RequestBody Payment payment
                                               ){
        Bill result = guestService.payBill(guestId,billId,payment);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value = "/{guestId}/add-order")
    public ResponseEntity<Bill> makeOrder(@PathVariable (value = "guestId") long guestId,
                                          @Valid @RequestBody Order order){
        Bill result = guestService.makeOrder(guestId,order);
        return new ResponseEntity<>(result,HttpStatus.CREATED);

    }
}
