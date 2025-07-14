package com.Man10h.BusTicket.service.impl;

import com.Man10h.BusTicket.convert.InvoiceConvert;
import com.Man10h.BusTicket.convert.TicketConvert;
import com.Man10h.BusTicket.exception.ex.BusErrorException;
import com.Man10h.BusTicket.exception.ex.TicketErrorException;
import com.Man10h.BusTicket.exception.ex.TripErrorException;
import com.Man10h.BusTicket.exception.ex.UserErrorException;
import com.Man10h.BusTicket.model.Status;
import com.Man10h.BusTicket.model.dto.TicketDTO;
import com.Man10h.BusTicket.model.entity.*;
import com.Man10h.BusTicket.model.response.InvoiceResponse;
import com.Man10h.BusTicket.model.response.TicketResponse;
import com.Man10h.BusTicket.repository.*;
import com.Man10h.BusTicket.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TicketRepository ticketRepository;
    private final SeatPriceRepository seatPriceRepository;
    private final BusRepository busRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketConvert ticketConvert;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceConvert invoiceConvert;

    @Override
    public TicketResponse createTicket(TicketDTO ticketDTO, String userId) {
        if(ticketDTO == null || userId == null || ticketDTO.getId() != null ||
        ticketDTO.getBusId() == null || ticketDTO.getTripId() == null || ticketDTO.getSeatPriceIdList() == null
         || ticketDTO.getSeatPriceIdList().isEmpty()) {
            return null;
        }
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isEmpty()) {
            throw new UserErrorException("User not found");
        }
        UserEntity userEntity = optionalUserEntity.get();

        Optional<BusEntity> optionalBusEntity = busRepository.findById(ticketDTO.getBusId());
        if(optionalBusEntity.isEmpty()) {
            throw new BusErrorException("Bus not found");
        }
        BusEntity busEntity = optionalBusEntity.get();

        Optional<TripEntity> optionalTripEntity = tripRepository.findById(ticketDTO.getTripId());
        if(optionalTripEntity.isEmpty()) {
            throw new TripErrorException("Trip not found");
        }
        TripEntity tripEntity = optionalTripEntity.get();

        int numberOfSeats = ticketDTO.getSeatPriceIdList().size();
        Double price = numberOfSeats * tripEntity.getSeatPriceEntityList().get(0).getPrice();

        //create Ticket
        TicketEntity ticketEntity = TicketEntity.builder()
                .busEntity(busEntity)
                .tripEntity(tripEntity)
                .userEntity(userEntity)
                .price(price)
                .dayCreated(new Date(new Date().getTime()))
                .seatPriceEntityList(new ArrayList<>())
                .status(false)
                .build();
        ticketRepository.save(ticketEntity);

        //logic seatPrice isAvailable -> false
        for(int i =0; i<ticketDTO.getSeatPriceIdList().size(); i++) {
            Optional<SeatPriceEntity> optionalSeatPriceEntity = seatPriceRepository.findById(ticketDTO.getSeatPriceIdList().get(i));
            if(optionalSeatPriceEntity.isEmpty()) {
                ticketRepository.delete(ticketEntity);
                return null;
            }
            SeatPriceEntity seatPriceEntity = optionalSeatPriceEntity.get();
            if(!seatPriceEntity.getIsAvailable()){
                ticketRepository.delete(ticketEntity);
                return null;
            }
            seatPriceEntity.setTicketEntity(ticketEntity);
            seatPriceEntity.setIsAvailable(false);
            seatPriceRepository.save(seatPriceEntity);
            ticketEntity.getSeatPriceEntityList().add(seatPriceEntity);
        }
        ticketRepository.save(ticketEntity);
        return ticketConvert.convert(ticketEntity);
    }

    @Override
    public List<TicketResponse> getAllTickets(String userId) {
        if(userId == null){
            return null;
        }
        List<TicketEntity> ticketEntityList = ticketRepository.findByUserEntity_Id(userId);
        if(ticketEntityList == null){
            return null;
        }
        return ticketEntityList.stream().map(ticketConvert::convert).collect(Collectors.toList());
    }

    @Override
    public InvoiceResponse createInvoice(Map<String, Object> payload, String userId) {
        if(payload == null || payload.isEmpty() || userId == null){
            return null;
        }
        List<Integer> ticketIds = (List<Integer>) payload.get("ticketIds");
        if(ticketIds == null || ticketIds.isEmpty()) {
            return null;
        }
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isEmpty()) {
            throw new UserErrorException("User not found");
        }
        UserEntity userEntity = optionalUserEntity.get();

        InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                .userEntity(userEntity)
                .status(Status.INVOICE_NOT_PAID)
                .dayCreated(new Date(new Date().getTime()))
                .ticketEntityList(new ArrayList<>())
                .build();
        invoiceRepository.save(invoiceEntity);

        Double price = 0.0;
        for(Integer ticketId : ticketIds) {
            Optional<TicketEntity> optionalTicketEntity = ticketRepository.findById((long) ticketId);
            if(optionalTicketEntity.isEmpty()) {
                invoiceRepository.delete(invoiceEntity);
                throw new TicketErrorException("Ticket not found");
            }
            TicketEntity ticketEntity = optionalTicketEntity.get();
            if(ticketEntity.getStatus()){
                invoiceRepository.delete(invoiceEntity);
                throw new TicketErrorException("Ticket is already paid");
            }
            if(ticketEntity.getInvoiceEntity() != null){
                invoiceRepository.delete(invoiceEntity);
                throw new TicketErrorException("Ticket is in the old Invoice");
            }
            ticketEntity.setInvoiceEntity(invoiceEntity);
            price+=ticketEntity.getPrice();
            ticketRepository.save(ticketEntity);
            invoiceEntity.getTicketEntityList().add(ticketEntity);
        }
        invoiceEntity.setPrice(price);
        invoiceRepository.save(invoiceEntity);
        return invoiceConvert.convert(invoiceEntity);
    }

    @Override
    public List<InvoiceResponse> getAllInvoices(String userId) {
        if(userId == null){
            return null;
        }
        List<InvoiceEntity> invoiceEntityList = invoiceRepository.findByUserEntity_Id(userId);
        if(invoiceEntityList == null){
            return null;
        }
        return invoiceEntityList.stream().map(invoiceConvert::convert).collect(Collectors.toList());
    }

    @Override
    public InvoiceResponse updateInvoiceIsPaid(Long invoiceId) {
        if(invoiceId == null){
            return null;
        }
        Optional<InvoiceEntity> optionalInvoiceEntity = invoiceRepository.findById(invoiceId);
        if(optionalInvoiceEntity.isEmpty()) {
            return null;
        }
        InvoiceEntity invoiceEntity = optionalInvoiceEntity.get();
        invoiceEntity.setStatus(Status.INVOICE_PAID);
        for(TicketEntity ticketEntity: invoiceEntity.getTicketEntityList()){
            ticketEntity.setStatus(true);
            ticketRepository.save(ticketEntity);
        }
        invoiceRepository.save(invoiceEntity);
        return invoiceConvert.convert(invoiceEntity);
    }
}
