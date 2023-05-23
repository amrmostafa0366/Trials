package com.trials.tryAll.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @NotBlank(message = "order description is required")
    private String orderDescription;

    //@NotBlank(message = "order cost is required")
    private double orderCost;
}
