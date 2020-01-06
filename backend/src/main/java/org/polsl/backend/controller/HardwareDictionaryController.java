package org.polsl.backend.controller;

import org.polsl.backend.service.HardwareDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie hardwareDictionary'em.
 */
@RestController
@RequestMapping("api/hardware-dictionaries")
public class HardwareDictionaryController {
  private final HardwareDictionaryService hardwareDictionaryService;

  @Autowired
  public HardwareDictionaryController(HardwareDictionaryService hardwareDictionaryService){
    this.hardwareDictionaryService = hardwareDictionaryService;
  }
  /**
   * Endpoint obsługujący uzyskiwanie listy wszystkich hardwareDictionary'ów.
   *
   * @return lista hardwareDictionary
   */
  @GetMapping
  public ResponseEntity<?> getHardwareDictionaryList() {
    return ResponseEntity.ok(hardwareDictionaryService.getHardwareDictionaryList());
  }
}
