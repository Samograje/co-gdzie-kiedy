package org.polsl.backend.service;

import org.polsl.backend.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardwareService {
    private HardwareRepository hardwareRepository;

    @Autowired
    public HardwareService(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }
}
