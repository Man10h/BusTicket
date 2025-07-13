package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    public List<InvoiceEntity> findByUserId(String userId);
}
