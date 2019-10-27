package org.polsl.backend.controller;

import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.Software;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.polsl.backend.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie statystykami na stronie głównej.
 */
@RestController
@RequestMapping("api/statistics") //TODO: ustalić endpoint strony głównej
public class StatisticsController {

  private final StatisticsService statisticsService;

  @Autowired
  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @GetMapping
  public ResponseEntity<?> getAllStatistics() {
    return ResponseEntity.ok(statisticsService.getAllStatistics());
  }

}
