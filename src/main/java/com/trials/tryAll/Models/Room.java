package com.trials.tryAll.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    @Column
    private Integer roomNumber;

    @JsonIgnoreProperties({"room", "reservations"})
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id",referencedColumnName = "guestId")
    private Guest guest;

    @JsonIgnoreProperties({"rooms","guest","room"})
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reservation> reservations;

}
