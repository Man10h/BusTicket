package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "busId")
    private BusEntity busEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
