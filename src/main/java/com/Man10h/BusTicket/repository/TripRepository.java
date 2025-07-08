package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {
}
