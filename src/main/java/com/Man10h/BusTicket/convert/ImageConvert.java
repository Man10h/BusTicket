package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.ImageEntity;
import com.Man10h.BusTicket.model.response.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageConvert {
    private final ModelMapper modelMapper;

    public ImageResponse convert(ImageEntity imageEntity){
        return modelMapper.map(imageEntity, ImageResponse.class);
    }
}
