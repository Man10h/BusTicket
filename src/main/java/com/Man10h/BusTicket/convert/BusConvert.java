package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.BusEntity;
import com.Man10h.BusTicket.model.response.BusResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusConvert {
    private final ModelMapper modelMapper;

    public BusResponse convert(BusEntity busEntity) {
        return modelMapper.map(busEntity, BusResponse.class);
    }
}
