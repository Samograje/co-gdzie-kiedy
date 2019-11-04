package org.polsl.backend.service;

import org.polsl.backend.dto.statistics.StatisticsOutputDTO;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Logika biznesowa statystyk dotyczących bazy danych.
 */
@Service
public class StatisticsService {
  private final AffiliationRepository affiliationRepository;
  private final HardwareRepository hardwareRepository;
  private final ComputerSetRepository computerSetRepository;
  private final SoftwareRepository softwareRepository;

  @Autowired
  public StatisticsService(AffiliationRepository affiliationRepository, HardwareRepository hardwareRepository,
                           ComputerSetRepository computerSetRepository, SoftwareRepository softwareRepository) {
    this.affiliationRepository = affiliationRepository;
    this.hardwareRepository = hardwareRepository;
    this.computerSetRepository = computerSetRepository;
    this.softwareRepository = softwareRepository;
  }

  public StatisticsOutputDTO getAllStatistics() {
    StatisticsOutputDTO statisticsOutputDTO = new StatisticsOutputDTO();
    statisticsOutputDTO.setAffiliationsCount(affiliationRepository.countByIsDeletedIsFalse());
    statisticsOutputDTO.setComputerSetsCount(computerSetRepository.countByValidToIsNull());
    statisticsOutputDTO.setHardwareCount(hardwareRepository.countByValidToIsNull());
    statisticsOutputDTO.setSoftwareCount(softwareRepository.countByValidToIsNull());

    return statisticsOutputDTO;
  }
}
