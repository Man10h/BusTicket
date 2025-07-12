package com.Man10h.BusTicket.model.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDetailResponse {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dateTime;
}
