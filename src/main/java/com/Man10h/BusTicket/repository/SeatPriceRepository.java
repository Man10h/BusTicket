package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.SeatPriceEntity;
import com.Man10h.BusTicket.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatPriceRepository extends JpaRepository<SeatPriceEntity, Long> {
    public List<SeatPriceEntity> findByTripEntity(TripEntity tripEntity);
}
