package com.Man10h.BusTicket.service.impl;

import com.Man10h.BusTicket.convert.BusConvert;
import com.Man10h.BusTicket.exception.ex.BusErrorException;
import com.Man10h.BusTicket.exception.ex.UserErrorException;
import com.Man10h.BusTicket.model.dto.BusDTO;
import com.Man10h.BusTicket.model.entity.BusEntity;
import com.Man10h.BusTicket.model.entity.ImageEntity;
import com.Man10h.BusTicket.model.entity.SeatEntity;
import com.Man10h.BusTicket.model.entity.UserEntity;
import com.Man10h.BusTicket.model.response.BusResponse;
import com.Man10h.BusTicket.repository.BusRepository;
import com.Man10h.BusTicket.repository.ImageRepository;
import com.Man10h.BusTicket.repository.SeatRepository;
import com.Man10h.BusTicket.repository.UserRepository;
import com.Man10h.BusTicket.service.BusService;
import com.Man10h.BusTicket.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusConvert busConvert;
    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Override
    public List<BusResponse> getBusByUserId(String userId, int page, int pageSize) {
        List<BusEntity> busEntityList = busRepository.findByUserEntity_Id(userId, PageRequest.of(page, pageSize));
        if(busEntityList.isEmpty()){
            return null;
        }
        return busEntityList.stream().map(busConvert::convert).toList();
    }

    @Override
    public BusResponse createBus(BusDTO busDTO, String userId) {
        if(busDTO.getId() != null || busDTO.getName() == null || userId == null || busDTO.getNumberOfSeats() == null) {
            return null;
        }
        Optional<UserEntity> optional = userRepository.findById(userId);
        if(optional.isEmpty()) {
            throw new UserErrorException("User not found");
        }
        UserEntity userEntity = optional.get();

        BusEntity busEntity = BusEntity.builder()
                .name(busDTO.getName())
                .numberOfSeats(busDTO.getNumberOfSeats())
                .imageEntityList(new ArrayList<>())
                .ticketEntityList(new ArrayList<>())
                .tripEntityList(new ArrayList<>())
                .userEntity(userEntity)
                .build();
        if(busDTO.getDescription() != null){
            busEntity.setDescription(busDTO.getDescription());
        }
        busRepository.save(busEntity);

        //create seat
        for(int i=0; i< busDTO.getNumberOfSeats(); i++){
            SeatEntity seatEntity = SeatEntity.builder()
                    .seatEntityPriceList(new ArrayList<>())
                    .busEntity(busEntity)
                    .name(i + "")
                    .build();
            seatRepository.save(seatEntity);
        }

        if(!busDTO.getMultipartFiles().isEmpty()){
            for(MultipartFile multipartFile : busDTO.getMultipartFiles()) {
                Map result = cloudinaryService.upload(multipartFile);
                ImageEntity imageEntity = ImageEntity.builder()
                        .url((String) result.get("url"))
                        .busEntity(busEntity)
                        .build();
                imageRepository.save(imageEntity);
            }
        }
        return busConvert.convert(busEntity);
    }

    @Override
    public BusResponse updateBus(BusDTO busDTO) {
        if(busDTO.getId() == null) {
            return null;
        }
        Optional<BusEntity> optionalBus = busRepository.findById(busDTO.getId());
        if(optionalBus.isEmpty()) {
            throw new BusErrorException("Bus not found");
        }
        BusEntity busEntity = optionalBus.get();
        if(busDTO.getDescription() != null) busEntity.setDescription(busDTO.getDescription());
        if(busDTO.getName() != null){
            busEntity.setName(busDTO.getName());
        }
        if(!busDTO.getMultipartFiles().isEmpty()){
            for(MultipartFile multipartFile : busDTO.getMultipartFiles()) {
                Map result = cloudinaryService.upload(multipartFile);
                ImageEntity imageEntity = ImageEntity.builder()
                        .url((String) result.get("url"))
                        .busEntity(busEntity)
                        .build();
                imageRepository.save(imageEntity);
            }
        }
        busRepository.save(busEntity);
        return busConvert.convert(busEntity);
    }

    @Override
    public void deleteBus(Long busId) {
        if(busId == null) {
            throw new BusErrorException("Bus id is null");
        }
        Optional<BusEntity> optionalBus = busRepository.findById(busId);
        if(optionalBus.isEmpty()) {
            throw new BusErrorException("Bus not found");
        }
        busRepository.delete(optionalBus.get());
    }

    @Override
    public BusResponse getBusById(Long busId) {
        if(busId == null) {
            throw new BusErrorException("Bus id is null");
        }
        Optional<BusEntity> optionalBus = busRepository.findById(busId);
        if(optionalBus.isEmpty()) {
            throw new BusErrorException("Bus not found");
        }
        BusEntity busEntity = optionalBus.get();
        return BusResponse.builder()
                .id(busEntity.getId())
                .name(busEntity.getName())
                .description(busEntity.getDescription())
                .imageEntityList(busEntity.getImageEntityList())
                .seatEntities(busEntity.getSeatEntityList())
                .build();
    }


}
