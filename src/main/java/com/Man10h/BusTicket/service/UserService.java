package com.Man10h.BusTicket.service;

import com.Man10h.BusTicket.model.dto.UserDTO;
import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.model.response.UserResponse;

import java.util.Map;

public interface UserService {
    public Map<String, String> getAllToken(Map<String, Object> payload);
    public String getToken(String refreshToken);
    public UserResponse updateUser(UserDTO userDTO);
    public UserResponse getUserById(String id);

}
