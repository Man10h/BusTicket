package com.Man10h.BusTicket.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private Long errorCode;
    private String errorMessage;
}
