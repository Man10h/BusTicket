package com.Man10h.BusTicket.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatPriceResponse {
    private Long id;
    private Double price;
    private Boolean isAvailable;
    private SeatResponse seatResponse;
}
