package com.Man10h.BusTicket.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Date dayCreated;
    private String status;

    @OneToMany(mappedBy = "invoiceEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<TicketEntity> ticketEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
