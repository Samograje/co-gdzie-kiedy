package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.hardware.HardwareDTO;
import org.polsl.backend.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   * @param request stuktura {@link HardwareDTO} zawierająca dane nowego sprzętu
   * @return informacja o poprawnym utworzeniu sprzętu
   */
  @PostMapping
  public ResponseEntity<?> createHardware(@Valid @RequestBody HardwareDTO request) {
    hardwareService.createHardware(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono sprzęt"));
  }

  /**
   * Endpoint obsługujący edycję parametrów przynależności.
   *
   * @param request stuktura {@link HardwareDTO} zawierająca nowe dane sprzętu
   * @return informacja o poprawnym zaktualizowaniu parametrów sprzętu
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editAffiliation(
      @PathVariable(value = "id") Long id,
      @Valid @RequestBody HardwareDTO request
  ) {
    hardwareService.editHardware(id, request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Zaktualizowano parametry sprzętu"));
  }

  /**
   * Endpoint obsługujący usuwanie sprzętu.
   *
   * @param id ID wybranego sprzętu
   * @return informacja o poprawnym usunięciu sprzętu
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteHardware(@PathVariable(value = "id") Long id) {
    hardwareService.deleteHardware(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto sprzęt"));
  }
}
