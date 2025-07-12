package com.Man10h.BusTicket.service;

import com.Man10h.BusTicket.model.dto.QueryDTO;
import com.Man10h.BusTicket.model.dto.TripDTO;
import com.Man10h.BusTicket.model.response.TripResponse;

import java.util.List;

public interface TripService {
    public TripResponse createTrip(TripDTO tripDTO, String userId);
    public TripResponse updateTrip(TripDTO tripDTO);
    public void deleteTrip(Long tripId);
    public List<TripResponse> find(QueryDTO queryDTO, int page, int pageSize);
    public TripResponse findById(Long tripId);
}
