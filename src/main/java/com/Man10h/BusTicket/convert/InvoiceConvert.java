package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.InvoiceEntity;
import com.Man10h.BusTicket.model.entity.TicketEntity;
import com.Man10h.BusTicket.model.response.InvoiceResponse;
import com.Man10h.BusTicket.model.response.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceConvert {
    private final ModelMapper modelMapper;
    private final TicketConvert ticketConvert;

    public InvoiceResponse convert(InvoiceEntity invoiceEntity) {
        InvoiceResponse invoiceResponse = modelMapper.map(invoiceEntity, InvoiceResponse.class);

        //tickets
        List<TicketEntity> ticketEntityList = invoiceEntity.getTicketEntityList();
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for(TicketEntity ticketEntity : ticketEntityList) {
            TicketResponse ticketResponse = ticketConvert.convert(ticketEntity);
            ticketResponses.add(ticketResponse);
        }
        invoiceResponse.setTicketResponses(ticketResponses);

        return invoiceResponse;
    }
}
