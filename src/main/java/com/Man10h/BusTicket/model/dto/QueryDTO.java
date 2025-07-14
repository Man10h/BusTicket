package com.Man10h.BusTicket.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QueryDTO {
    private String departure;
    private String destination;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dateTime;
}
