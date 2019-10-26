package org.polsl.backend.controller;

import org.polsl.backend.service.SoftwareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie oprogramowaniem.
 */
@RestController
@RequestMapping("api/software")
public class SoftwareController {
  private final SoftwareService softwareService;

  public SoftwareController(SoftwareService softwareService) {
    this.softwareService = softwareService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy oprogramowania.
   *
   * @return lista oprogramowania
   */
  @GetMapping
  public ResponseEntity<?> getAllSoftware() {
    return ResponseEntity.ok(softwareService.getAllSoftware());
  }


}
