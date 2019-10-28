package org.polsl.backend.controller;

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
@RequestMapping("api/statistics")
public class StatisticsController {

  private final StatisticsService statisticsService;

  @Autowired
  public StatisticsController(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie statystyk z bazy danych.
   *
   * @return statystyki z bazy danych
   */
  @GetMapping
  public ResponseEntity<?> getAllStatistics() {
    return ResponseEntity.ok(statisticsService.getAllStatistics());
  }

}
