package com.Man10h.BusTicket.service.impl;

import com.Man10h.BusTicket.convert.TripConvert;
import com.Man10h.BusTicket.exception.ex.BusErrorException;
import com.Man10h.BusTicket.exception.ex.TripErrorException;
import com.Man10h.BusTicket.exception.ex.UserErrorException;
import com.Man10h.BusTicket.model.dto.QueryDTO;
import com.Man10h.BusTicket.model.dto.TripDTO;
import com.Man10h.BusTicket.model.entity.*;
import com.Man10h.BusTicket.model.response.TripResponse;
import com.Man10h.BusTicket.repository.*;
import com.Man10h.BusTicket.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;
    private final SeatPriceRepository seatPriceRepository;
    private final TripDetailRepository tripDetailRepository;
    private final TripConvert tripConvert;

    @Override
    public TripResponse createTrip(TripDTO tripDTO, String userId) {
        if(tripDTO == null){
            return null;
        }
        if(tripDTO.getId() != null || tripDTO.getBusId() == null ||
            tripDTO.getStartTime() == null || tripDTO.getEndTime() == null ||
            tripDTO.getPrice() == null || tripDTO.getPrice() < 0 ||
            tripDTO.getDestination() == null || tripDTO.getDeparture() == null ||
            tripDTO.getDateTimes() == null || tripDTO.getEndTime().isBefore(tripDTO.getStartTime())){
            return null;
        }
        Optional<BusEntity> optionalBusEntity = busRepository.findById(tripDTO.getBusId());
        if(optionalBusEntity.isEmpty()){
            throw new BusErrorException("Bus not found");
        }
        BusEntity busEntity = optionalBusEntity.get();
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isEmpty()){
            throw new UserErrorException("User nor found");
        }
        UserEntity userEntity = optionalUserEntity.get();

        TripEntity tripEntity = TripEntity.builder()
                .busEntity(busEntity)
                .userEntity(userEntity)
                .tripDetailEntityList(new ArrayList<>())
                .seatPriceEntityList(new ArrayList<>())
                .departure(tripDTO.getDeparture())
                .destination(tripDTO.getDestination())
                .build();
        if(tripDTO.getDescription() != null){
            tripEntity.setDescription(tripDTO.getDescription());
        }
        tripRepository.save(tripEntity);

        //create seatPrice
        Long numberOfSeats = busEntity.getNumberOfSeats();
        for(int i=0; i<numberOfSeats; i++){
            SeatPriceEntity seatPriceEntity = SeatPriceEntity.builder()
                    .price(tripDTO.getPrice())
                    .ticketEntity(null)
                    .isAvailable(true)
                    .seatEntity(busEntity.getSeatEntityList().get(i))
                    .tripEntity(tripEntity)
                    .build();
            seatPriceRepository.save(seatPriceEntity);
        }

        //create tripDetail
        for(LocalDate localDate: tripDTO.getDateTimes()){
            TripDetailEntity tripDetailEntity = TripDetailEntity.builder()
                    .tripEntity(tripEntity)
                    .dateTime(localDate)
                    .startTime(tripDTO.getStartTime())
                    .endTime(tripDTO.getEndTime())
                    .build();
            tripDetailRepository.save(tripDetailEntity);
        }
        tripRepository.save(tripEntity);

        return tripConvert.convertAll(tripEntity);
    }

    @Override
    public TripResponse updateTrip(TripDTO tripDTO) {
        if(tripDTO == null){
            return null;
        }
        if(tripDTO.getId() == null){
            return null;
        }
        Optional<TripEntity> optionalTripEntity = tripRepository.findById(tripDTO.getId());
        if(optionalTripEntity.isEmpty()){
            throw new TripErrorException("Trip not found");
        }
        TripEntity tripEntity = optionalTripEntity.get();

        if(tripDTO.getDescription() != null){
            tripEntity.setDescription(tripDTO.getDescription());
        }
        if(tripDTO.getDestination() != null){
            tripEntity.setDestination(tripDTO.getDestination());
        }
        if(tripDTO.getDeparture() != null){
            tripEntity.setDeparture(tripDTO.getDeparture());
        }

        if(tripDTO.getStartTime() != null){
            List<TripDetailEntity> tripDetailEntityList = tripDetailRepository.findByTripEntity(tripEntity);
            if(tripDetailEntityList.get(0).getEndTime().isBefore(tripDTO.getStartTime())){
                return null;
            }
            for(TripDetailEntity tripDetailEntity: tripDetailEntityList){
                tripDetailEntity.setStartTime(tripDTO.getStartTime());
                tripDetailRepository.save(tripDetailEntity);
            }
        }

        if(tripDTO.getEndTime() != null){
            List<TripDetailEntity> tripDetailEntityList = tripDetailRepository.findByTripEntity(tripEntity);
            if(tripDetailEntityList.get(0).getStartTime().isAfter(tripDTO.getStartTime())){
                return null;
            }
            for(TripDetailEntity tripDetailEntity: tripDetailEntityList){
                tripDetailEntity.setStartTime(tripDTO.getStartTime());
                tripDetailRepository.save(tripDetailEntity);
            }
        }

        if(tripDTO.getPrice() != null){
            if(tripDTO.getPrice() < 0){
                return null;
            }
            List<SeatPriceEntity> seatPriceEntityList = seatPriceRepository.findByTripEntity(tripEntity);
            for(SeatPriceEntity seatPriceEntity: seatPriceEntityList){
                seatPriceEntity.setPrice(tripDTO.getPrice());
                seatPriceRepository.save(seatPriceEntity);
            }
        }

        tripRepository.save(tripEntity);
        return tripConvert.convertAll(tripEntity);
    }

    @Override
    public void deleteTrip(Long tripId) {
        if(tripId == null){
            return ;
        }
        Optional<TripEntity> optional = tripRepository.findById(tripId);
        if(optional.isEmpty()){
            throw new TripErrorException("Trip not found");
        }
        TripEntity tripEntity = optional.get();
        tripRepository.delete(tripEntity);
    }

    @Override
    public List<TripResponse> find(QueryDTO queryDTO, int page, int pageSize) {
        Page<TripEntity> tripEntityList = tripRepository.findByQuery(queryDTO.getDestination(),
                queryDTO.getDeparture(),
                queryDTO.getStartTime(),
                queryDTO.getEndTime(),
                queryDTO.getDateTime()
        , PageRequest.of(page, pageSize, Sort.by("id").ascending()));
        if(tripEntityList == null || tripEntityList.isEmpty()){
            return null;
        }
        return tripEntityList.stream()
                .map(tripConvert::convert)
                .toList();
    }

    @Override
    public TripResponse findById(Long tripId) {
        if(tripId == null){
            return null;
        }
        Optional<TripEntity> optional = tripRepository.findById(tripId);
        if(optional.isEmpty()){
            throw new TripErrorException("Trip not found");
        }
        TripEntity tripEntity = optional.get();
        return tripConvert.convertAll(tripEntity);
    }

    @Override
    public List<TripResponse> getRecommendationTrips(String userId) {
        if(userId == null){
            return null;
        }
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isEmpty()){
            throw new UserErrorException("User not found");
        }
        String mostDestination = null;
        List<Object[]> tripEntityListByDestination = tripRepository.statisticsTripsByDestination(userId);
        if(tripEntityListByDestination != null && !tripEntityListByDestination.isEmpty()){
            mostDestination = (String) tripEntityListByDestination.get(0)[0];
        }
        String mostDeparture = null;
        List<Object[]> tripEntityListByDeparture = tripRepository.statisticsTripsByDeparture(userId);
        if(tripEntityListByDeparture != null && !tripEntityListByDeparture.isEmpty()){
            mostDeparture = (String) tripEntityListByDeparture.get(0)[0];
        }
        List<TripEntity> tripEntityList = tripRepository.findByDestinationAndDeparture(mostDestination, mostDeparture);
        if(tripEntityList == null || tripEntityList.isEmpty()){
            return null;
        }
        return tripEntityList.stream().map(tripConvert::convert).toList();
    }
}
