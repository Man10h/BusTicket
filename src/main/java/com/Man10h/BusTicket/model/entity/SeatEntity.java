package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "busId")
    private BusEntity busEntity;

    @OneToMany(mappedBy = "seatEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<SeatPriceEntity> seatEntityPriceList = new ArrayList<>();
}
