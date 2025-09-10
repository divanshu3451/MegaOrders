package com.megaorders.dtos.dummy;

import com.megaorders.models.enums.DeliveryStatus;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItemTrackingCsvDTO {
    private String serialNumber;
    private String transactionId;
    private DeliveryStatus status;
    @CsvDate("yyyy-MM-dd")
    private LocalDate packedDate;
    @CsvDate("HH:mm:ss")
    private LocalTime packedTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate dispatchDate;
    @CsvDate("HH:mm:ss")
    private LocalTime dispatchTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate nearestHubDate;
    @CsvDate("HH:mm:ss")
    private LocalTime nearestHubTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate outForDeliveryDate;
    @CsvDate("HH:mm:ss")
    private LocalTime outForDeliveryTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate deliveryDate;
    @CsvDate("HH:mm:ss")
    private LocalTime deliveryTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate returnExpireDate;
    @CsvDate("HH:mm:ss")
    private LocalTime returnExpireTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate returnRequestedDate;
    @CsvDate("HH:mm:ss")
    private LocalTime returnRequestedTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate returnAcceptedDate;
    @CsvDate("HH:mm:ss")
    private LocalTime returnAcceptedTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate returnPickUpDate;
    @CsvDate("HH:mm:ss")
    private LocalTime returnPickUpTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate refundDate;
    @CsvDate("HH:mm:ss")
    private LocalTime refundTime;
    @CsvDate("yyyy-MM-dd")
    private LocalDate returnRejectedDate;
    @CsvDate("HH:mm:ss")
    private LocalTime returnRejectedTime;
}
