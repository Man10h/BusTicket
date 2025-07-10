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
    //attribute model mapper
    private Long id;
    private String name;
    private String description;
    private List<ImageEntity> imageEntityList;
    private Long numberOfSeats;

    //attribute not model mapper
    private List<SeatEntity> seatEntities;
}
