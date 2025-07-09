package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ticket")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Date dayCreated;

    @OneToMany(mappedBy = "ticketEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<SeatPriceEntity> seatPriceEntityList;

    @ManyToOne
    @JoinColumn(name = "busId")
    private BusEntity busEntity;

    @ManyToOne
    @JoinColumn(name = "invoiceId")
    private InvoiceEntity invoiceEntity;

    @ManyToOne
    @JoinColumn(name = "tripId")
    private TripEntity tripEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
