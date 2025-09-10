package com.megaorders.models.embeddables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryInfo {
    private LocalDate orderPlacedDate;
    private LocalTime orderPlacedTime;
    private LocalDate packedDate;
    private LocalTime packedTime;
    private LocalDate dispatchDate;
    private LocalTime dispatchTime;
    private LocalDate nearestHubDate;
    private LocalTime nearestHubTime;
    private LocalDate outForDeliveryDate;
    private LocalTime outForDeliveryTime;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
}
