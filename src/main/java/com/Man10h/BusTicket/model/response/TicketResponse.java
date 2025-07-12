package com.Man10h.BusTicket.model.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private Double price;
    private Date dayCreated;

    private List<SeatPriceResponse> seatPriceResponses;
    private TripResponse tripResponse;
}
