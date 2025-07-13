package com.Man10h.BusTicket.service;

import com.Man10h.BusTicket.model.dto.TicketDTO;
import com.Man10h.BusTicket.model.response.InvoiceResponse;
import com.Man10h.BusTicket.model.response.TicketResponse;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    public TicketResponse createTicket(TicketDTO ticketDTO, String userId);
    public List<TicketResponse> getAllTickets(String userId);
    public InvoiceResponse createInvoice(Map<String, Object> payload, String userId);
    public List<InvoiceResponse> getAllInvoices(String userId);
    public InvoiceResponse updateInvoiceIsPaid(Long invoiceId);
}
