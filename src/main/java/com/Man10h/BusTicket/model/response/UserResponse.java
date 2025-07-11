package com.Man10h.BusTicket.model.response;

import com.Man10h.BusTicket.model.entity.ImageEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private List<ImageResponse> imageResponses;
    private String role;
}
