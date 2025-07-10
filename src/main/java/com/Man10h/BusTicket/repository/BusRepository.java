package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.BusEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {
    List<BusEntity> findByUserEntity_Id(String id, Pageable pageable);
}
