package com.Man10h.BusTicket.convert;

import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConvert {
    private final ModelMapper modelMapper;

    public UserResponse convert(UserEntity userEntity) {
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        //role
        userResponse.setRole(userEntity.getRoleEntity().getName());

        return userResponse;
    }
}
