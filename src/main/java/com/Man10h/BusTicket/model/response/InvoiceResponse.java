package com.Man10h.BusTicket.model.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private Long id;
    private Double price;
    private String status;
    private Date dayCreated;

    private List<TicketResponse> ticketResponses;
}
