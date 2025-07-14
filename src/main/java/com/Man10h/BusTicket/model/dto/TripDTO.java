package com.Man10h.BusTicket.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {
    private Long id;
    private String departure;
    private String destination;
    private String description;
    private List<LocalDate> dateTimes;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double price;

    private Long busId;

    public String toString(){
        return departure + " " + destination + " " + description + " " + startTime + " " + endTime;
    }
}
