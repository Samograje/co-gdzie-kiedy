package org.polsl.inventory.service;

import org.polsl.inventory.repository.HardwareRepository;
import org.springframework.stereotype.Service;

@Service
public class HardwareDictionaryService {
    private HardwareRepository hardwareRepository;

    public HardwareDictionaryService(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }
}
