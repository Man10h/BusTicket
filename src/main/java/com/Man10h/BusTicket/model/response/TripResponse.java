package com.Man10h.BusTicket.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripResponse {
    private Long id;
    private String departure;
    private String destination;
    private String description;
    private BusResponse busResponse;
    private List<TripDetailResponse> tripDetailResponses;
    private List<SeatPriceResponse> seatPriceResponses;
}
