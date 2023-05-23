package com.trials.tryAll.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long guestId;

    @NotBlank(message = "Name is required")
    @Size(max = 50,min =3,message = "Name must not exceed 50 characters and can't be less than 3 characters")
    @Column
    private String guestName;

    @JsonIgnoreProperties({"guest","reservations"})
    @OneToOne(mappedBy = "guest",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Room room;

    @JsonIgnoreProperties({"guest","room"})
    @OneToMany(mappedBy = "guest",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @JsonIgnoreProperties({"guest"})
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Bill> bills;
}
