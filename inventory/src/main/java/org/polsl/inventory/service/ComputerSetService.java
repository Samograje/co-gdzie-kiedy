package org.polsl.inventory.service;

import org.polsl.inventory.repository.ComputerSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerSetService {
    private ComputerSetRepository computerSetRepository;

    @Autowired
    public ComputerSetService(ComputerSetRepository computerSetRepository) {
        this.computerSetRepository = computerSetRepository;
    }
}
