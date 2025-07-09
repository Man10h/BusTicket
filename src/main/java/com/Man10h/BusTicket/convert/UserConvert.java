package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.ImageEntity;
import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.model.response.ImageResponse;
import com.Man10h.BusTicket.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConvert {
    private final ModelMapper modelMapper;

    public UserResponse convert(UserEntity userEntity) {
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);

        //image
        List<ImageResponse> imageResponses = new ArrayList<>();
        for(ImageEntity imageEntity: userEntity.getImageEntityList()){
            ImageResponse imageResponse = modelMapper.map(imageEntity, ImageResponse.class);
            imageResponses.add(imageResponse);
        }
        userResponse.setImageResponses(imageResponses);
        //role
        userResponse.setRole(userEntity.getRoleEntity().getName());

        return userResponse;
    }
}
