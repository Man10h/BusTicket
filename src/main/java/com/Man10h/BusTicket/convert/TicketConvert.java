package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.*;
import com.Man10h.BusTicket.model.response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketConvert {
    private final ModelMapper modelMapper;

    public TicketResponse convert(TicketEntity ticketEntity) {
        TicketResponse ticketResponse = modelMapper.map(ticketEntity, TicketResponse.class);

        //trip
        TripEntity tripEntity = ticketEntity.getTripEntity();
        TripResponse tripResponse = modelMapper.map(tripEntity, TripResponse.class);
        BusEntity busEntity = tripEntity.getBusEntity();
        BusResponse busResponse = modelMapper.map(busEntity, BusResponse.class);
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity: busEntity.getImageEntityList()){
            ImageResponse imageResponse = modelMapper.map(imageEntity, ImageResponse.class);
            imageResponses.add(imageResponse);
        }
        busResponse.setImageResponses(imageResponses);
        tripResponse.setBusResponse(busResponse);
        ticketResponse.setTripResponse(tripResponse);

        //seatPrice
        List<SeatPriceEntity> seatPriceEntityList = ticketEntity.getSeatPriceEntityList();
        List<SeatPriceResponse> seatPriceResponses = new ArrayList<>();
        for(SeatPriceEntity seatPriceEntity: seatPriceEntityList){
            SeatPriceResponse seatPriceResponse = modelMapper.map(seatPriceEntity, SeatPriceResponse.class);
            SeatEntity seatEntity = seatPriceEntity.getSeatEntity();
            SeatResponse seatResponse = modelMapper.map(seatEntity, SeatResponse.class);
            seatPriceResponse.setSeatResponse(seatResponse);
            seatPriceResponses.add(seatPriceResponse);
        }
        ticketResponse.setSeatPriceResponses(seatPriceResponses);

        return ticketResponse;
    }
}
