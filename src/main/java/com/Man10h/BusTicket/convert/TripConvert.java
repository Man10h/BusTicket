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
public class TripConvert {
    private final ModelMapper modelMapper;

    public TripResponse convert(TripEntity tripEntity) {
        TripResponse tripResponse = modelMapper.map(tripEntity, TripResponse.class);

        //bus
        BusEntity busEntity = tripEntity.getBusEntity();
        BusResponse busResponse = modelMapper.map(busEntity, BusResponse.class);
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity: busEntity.getImageEntityList()){
            ImageResponse imageResponse = modelMapper.map(imageEntity, ImageResponse.class);
            imageResponses.add(imageResponse);
        }
        busResponse.setImageResponses(imageResponses);
        tripResponse.setBusResponse(busResponse);

        return tripResponse;
    }

    public TripResponse convertAll(TripEntity tripEntity) {
        TripResponse tripResponse = modelMapper.map(tripEntity, TripResponse.class);

        //bus
        BusEntity busEntity = tripEntity.getBusEntity();
        BusResponse busResponse = modelMapper.map(busEntity, BusResponse.class);
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity: busEntity.getImageEntityList()){
            ImageResponse imageResponse = modelMapper.map(imageEntity, ImageResponse.class);
            imageResponses.add(imageResponse);
        }
        busResponse.setImageResponses(imageResponses);
        tripResponse.setBusResponse(busResponse);

        //SeatPrice
        List<SeatPriceResponse> seatPriceResponses = new ArrayList<>();
        for(SeatPriceEntity seatPriceEntity: tripEntity.getSeatPriceEntityList()){
            SeatPriceResponse seatPriceResponse = modelMapper.map(seatPriceEntity, SeatPriceResponse.class);
            SeatEntity seatEntity = seatPriceEntity.getSeatEntity();
            SeatResponse seatResponse = modelMapper.map(seatEntity, SeatResponse.class);
            seatPriceResponse.setSeatResponse(seatResponse);

            seatPriceResponses.add(seatPriceResponse);
        }
        tripResponse.setSeatPriceResponses(seatPriceResponses);

        //TripDetail
        List<TripDetailResponse> tripDetailResponses = new ArrayList<>();
        for(TripDetailEntity tripDetailEntity: tripEntity.getTripDetailEntityList()){
            TripDetailResponse tripDetailResponse = modelMapper.map(tripDetailEntity, TripDetailResponse.class);
            tripDetailResponses.add(tripDetailResponse);
        }
        tripResponse.setTripDetailResponses(tripDetailResponses);

        return tripResponse;
    }
}
