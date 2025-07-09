package com.Man10h.BusTicket.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private List<MultipartFile> multipartFiles;
    private Long roleId;
}
