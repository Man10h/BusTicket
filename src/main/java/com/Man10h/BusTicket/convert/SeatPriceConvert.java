package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.SeatEntity;
import com.Man10h.BusTicket.model.entity.SeatPriceEntity;
import com.Man10h.BusTicket.model.entity.TripEntity;
import com.Man10h.BusTicket.model.response.SeatPriceResponse;
import com.Man10h.BusTicket.model.response.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatPriceConvert {
    private final ModelMapper modelMapper;

    public SeatPriceResponse convert(SeatPriceEntity seatPriceEntity) {
        SeatPriceResponse seatPriceResponse = modelMapper.map(seatPriceEntity, SeatPriceResponse.class);
        SeatEntity seatEntity = seatPriceEntity.getSeatEntity();
        SeatResponse seatResponse = modelMapper.map(seatEntity, SeatResponse.class);
        seatPriceResponse.setSeatResponse(seatResponse);
        return seatPriceResponse;
    }
}
