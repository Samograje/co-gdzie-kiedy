package org.polsl.backend.controller;

import org.polsl.backend.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie sprzętem.
 */
@RestController
@RequestMapping("api/devices")
public class DeviceController {
  private final DeviceService deviceService;

  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy sprzętu.
   *
   * @return lista sprzętu
   */
  @GetMapping
  public ResponseEntity<?> getAllDevices() {
    return ResponseEntity.ok(deviceService.getAllHardwareAndComputerSets());
  }
}
