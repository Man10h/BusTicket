package com.Man10h.BusTicket.model.response;

import com.Man10h.BusTicket.model.entity.ImageEntity;
import com.Man10h.BusTicket.model.entity.SeatEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusResponse {
    private Long id;
    private String name;
    private String description;
    private Long numberOfSeats;
    private List<SeatResponse> seatResponses;
    private List<ImageResponse> imageResponses;
}
