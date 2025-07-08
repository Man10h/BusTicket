package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.SeatPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatPriceRepository extends JpaRepository<SeatPriceEntity, Long> {
}
