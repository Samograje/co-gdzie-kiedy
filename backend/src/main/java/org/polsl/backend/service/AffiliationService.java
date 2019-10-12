package org.polsl.backend.service;

import org.polsl.backend.repository.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliationService {
    private AffiliationRepository affiliationRepository;

    @Autowired
    public AffiliationService(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }
}
