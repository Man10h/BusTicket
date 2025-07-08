package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seatPrice")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "seatId")
    private SeatEntity seatEntity;


    @ManyToOne
    @JoinColumn(name = "tripId")
    private TripEntity tripEntity;
}
