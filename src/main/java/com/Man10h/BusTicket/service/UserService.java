package com.Man10h.BusTicket.service;

import java.util.Map;

public interface UserService {
    public Map<String, String> getToken(Map<String, Object> payload);
}
