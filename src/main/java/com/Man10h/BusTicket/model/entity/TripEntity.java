package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trip")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String departure;
    private String destination;

    private Date dayBegin;
    private Date startTime;
    private Date endTime;

    private String description;

    @ManyToOne
    @JoinColumn(name = "busId")
    private BusEntity busEntity;

    @OneToMany(mappedBy = "tripEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<TicketEntity> ticketEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "tripEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<SeatPriceEntity> seatPriceEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
