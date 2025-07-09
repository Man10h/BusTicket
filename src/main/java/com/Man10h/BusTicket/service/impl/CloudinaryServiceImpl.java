package com.Man10h.BusTicket.service.impl;

import com.Man10h.BusTicket.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) {
        try {
            return cloudinary.uploader().upload(file, new HashMap<String, Object>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
