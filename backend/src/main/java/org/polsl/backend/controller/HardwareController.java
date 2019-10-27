package org.polsl.backend.controller;

import org.polsl.backend.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie hardware'm.
 */
@RestController
@RequestMapping("api/hardware")
public class HardwareController {
  private final HardwareService hardwareService;

  @Autowired
  public HardwareController(HardwareService hardwareService) {
    this.hardwareService = hardwareService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy hardware'u niebędącego składowym żadnego zestawu komputerowego.
   *
   * @return lista hardware'u niebędącego składowym żadnego zestawu komputerowego
   */
  @GetMapping
  public ResponseEntity<?> getAllSoloHardware() {
    return ResponseEntity.ok(hardwareService.getAllSoloHardware());
  }
}
