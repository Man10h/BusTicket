package com.Man10h.BusTicket.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Long id;
    private Double price;

    private List<Long> seatPriceIdList;
    private Long busId;
    private Long tripId;
}
