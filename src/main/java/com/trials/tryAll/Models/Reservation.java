package com.trials.tryAll.Models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;

    @JsonIgnoreProperties("reservations")
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @JsonIgnoreProperties("reservations")
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull(message = "Check-in date is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date checkInDate;

    @NotNull(message = "Check-out date is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date checkOutDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date timestamp;

    public Reservation(){
        this.timestamp = new Date();
    }

    public Reservation( Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this();
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

}
