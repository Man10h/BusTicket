package com.Man10h.BusTicket.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {
    private Long id;
    private String name;
    private String description;
    private List<MultipartFile> multipartFiles;
    private Long numberOfSeats;
}
