package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tripDetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dateTime;

    @ManyToOne
    @JoinColumn(name = "tripId")
    private TripEntity tripEntity;
}
