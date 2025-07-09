package com.Man10h.BusTicket.model.response;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private Long id;
    private String url;
}
