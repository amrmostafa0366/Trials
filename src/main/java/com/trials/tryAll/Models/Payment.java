package com.trials.tryAll.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
public class Payment {
    @Column
    private String paymentMethod;

    @Column
    private Boolean payed;
    private Date paymentDate;

    public Payment() {
        this.payed = false;
    }

    public Payment(String paymentMethod, boolean payed) {
        this();
        this.paymentMethod = paymentMethod;
        this.payed = payed;
    }
}
