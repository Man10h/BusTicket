package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.TripDetailEntity;
import com.Man10h.BusTicket.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripDetailRepository extends JpaRepository<TripDetailEntity, Long> {
    public List<TripDetailEntity> findByTripEntity(TripEntity tripEntity);
}
