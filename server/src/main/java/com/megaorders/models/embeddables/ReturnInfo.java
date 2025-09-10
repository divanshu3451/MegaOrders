package com.megaorders.models.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReturnInfo {
    private LocalDate returnExpireDate;
    private LocalTime returnExpireTime;
    private LocalDate returnRequestedDate;
    private LocalTime returnRequestedTime;
    private LocalDate returnAcceptedDate;
    private LocalTime returnAcceptedTime;
    private LocalDate returnPickUpDate;
    private LocalTime returnPickUpTime;
    private LocalDate refundDate;
    private LocalTime refundTime;
    private LocalDate returnRejectedDate;
    private LocalTime returnRejectedTime;
}
