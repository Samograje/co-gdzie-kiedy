package org.polsl.backend.service;

import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftwareService {
    private SoftwareRepository softwareRepository;

    @Autowired
    public SoftwareService(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }
}
