package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Date day;

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
