package com.Man10h.BusTicket.repository;

import com.Man10h.BusTicket.model.entity.TripEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {
    @Query("SELECT t FROM TripEntity t " +
            "JOIN TripDetailEntity td ON t.id = td.tripEntity.id " +
            "WHERE (:destination IS NULL OR t.destination LIKE CONCAT('%', :destination, '%')) " +
            "AND (:departure IS NULL OR t.departure LIKE CONCAT('%', :departure, '%')) " +
            "AND (:start_time IS NULL OR td.startTime = :start_time) " +
            "AND (:end_time IS NULL OR td.endTime = :end_time) " +
            "AND (:date_time IS NULL OR td.dateTime = :date_time)")
    Page<TripEntity> findByQuery(@Param("destination") String destination,
                                 @Param("departure") String departure,
                                 @Param("start_time") LocalTime start_time,
                                 @Param("end_time") LocalTime end_time,
                                 @Param("date_time") LocalDate date_time,
                                 Pageable pageable);


    @Query("SELECT t FROM TripEntity t " +
            "WHERE (?1 IS  NULL OR t.destination LIKE CONCAT('%', ?1, '%')) " +
            "OR (?2 IS NULL OR t.departure LIKE CONCAT('%', ?2, '%')) " +
            "ORDER BY t.id DESC LIMIT 10 ")
    List<TripEntity> findByDestinationAndDeparture(String destination, String departure);


    @Query("SELECT t FROM TripEntity t " +
            "WHERE t.id IN (SELECT tr.id FROM TripEntity tr WHERE (?1 IS NULL OR tr.userEntity.id = ?1) ORDER BY tr.id DESC LIMIT 50) " +
            "GROUP BY t.destination " +
            "ORDER BY count(*) DESC ")
    List<TripEntity> statisticsTripsByDestination(String userId);

    @Query("SELECT t FROM TripEntity t " +
            "WHERE t.id IN (SELECT tr.id FROM TripEntity tr WHERE (?1 IS NULL OR tr.userEntity.id = ?1) ORDER BY tr.id DESC LIMIT 50) " +
            "GROUP BY t.departure " +
            "ORDER BY count(*) DESC ")
    List<TripEntity> statisticsTripsByDeparture(String userId);
}
