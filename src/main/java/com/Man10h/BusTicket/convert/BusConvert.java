package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.BusEntity;
import com.Man10h.BusTicket.model.entity.ImageEntity;
import com.Man10h.BusTicket.model.entity.SeatEntity;
import com.Man10h.BusTicket.model.response.BusResponse;
import com.Man10h.BusTicket.model.response.ImageResponse;
import com.Man10h.BusTicket.model.response.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BusConvert {
    private final ModelMapper modelMapper;

    public BusResponse convert(BusEntity busEntity) {
        BusResponse busResponse = modelMapper.map(busEntity, BusResponse.class);
        // image
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity: busEntity.getImageEntityList()){
            ImageResponse imageResponse = modelMapper.map(imageEntity, ImageResponse.class);
            imageResponses.add(imageResponse);
        }
        busResponse.setImageResponses(imageResponses);


        return busResponse;
    }


    public BusResponse convertAll(BusEntity busEntity) {
        BusResponse busResponse = modelMapper.map(busEntity, BusResponse.class);
        // image
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity: busEntity.getImageEntityList()){
            ImageResponse imageResponse = modelMapper.map(imageEntity, ImageResponse.class);
            imageResponses.add(imageResponse);
        }
        busResponse.setImageResponses(imageResponses);

        List<SeatResponse> seatResponses = new ArrayList<>();
        for(SeatEntity seatEntity: busEntity.getSeatEntityList()){
            SeatResponse seatResponse = modelMapper.map(seatEntity, SeatResponse.class);
            seatResponses.add(seatResponse);
        }
        busResponse.setSeatResponses(seatResponses);

        return busResponse;
    }
}
