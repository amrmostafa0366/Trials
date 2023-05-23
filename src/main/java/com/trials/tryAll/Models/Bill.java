package com.trials.tryAll.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Amount must be a positive value")
    @Column
    private double amount;

    @NotBlank(message = "Details cannot be blank")
    @Column
    private String details;

    @Embedded
    @Column
    private Payment payment;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date billDate;

    @JsonIgnoreProperties("bills")
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    public Bill() {
        this.billDate = new Date();
        this.payment = new Payment();
    }
    public Bill(double amount, String details, Guest guest) {
        this();
        this.amount = amount;
        this.details = details;
        this.guest = guest;
    }
}
