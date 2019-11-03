package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.hardware.HardwareInputDTO;
import org.polsl.backend.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

  /**
   * Endpoint obsługujący dodawanie nowego sprzętu.
   *
   * @param request stuktura {@link HardwareInputDTO} zawierająca dane nowego sprzętu
   * @return informacja o poprawnym utworzeniu sprzętu
   */
  @PostMapping
  public ResponseEntity<?> createHardware(@Valid @RequestBody HardwareInputDTO request) {
    hardwareService.createHardware(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono sprzęt"));
  }
}
