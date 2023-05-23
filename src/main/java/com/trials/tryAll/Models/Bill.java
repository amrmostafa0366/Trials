package com.trials.tryAll.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double amount;

    @Column
    private String details;

    @Column
    private Date billDate;

    @JsonIgnoreProperties("bills")
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    public Bill() {
        this.billDate = new Date();
    }
    public Bill(double amount, String details, Guest guest) {
        this();
        this.amount = amount;
        this.details = details;
        this.guest = guest;
    }
}
