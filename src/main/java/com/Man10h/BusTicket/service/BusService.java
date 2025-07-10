package com.Man10h.BusTicket.service;

import com.Man10h.BusTicket.model.dto.BusDTO;
import com.Man10h.BusTicket.model.response.BusResponse;

import java.util.List;

public interface BusService {
    public List<BusResponse> getBusByUserId(String userId, int page, int pageSize);
    public BusResponse createBus(BusDTO busDTO, String userId);
    public BusResponse updateBus(BusDTO busDTO);
    public void deleteBus(Long busId);
    public BusResponse getBusById(Long busId);
}
