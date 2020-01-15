package org.polsl.backend.controller;

import org.polsl.backend.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie hardwareDictionary'em.
 */
@RestController
@RequestMapping("api/export")
public class ExportController {
  private final ExportService exportService;

  @Autowired
  public ExportController(ExportService exportService){
    this.exportService = exportService;
  }
  /**
   * Endpoint obsługujący uzyskiwanie pliku Pdf z listą danych z określonej encji.
   *
   * @param type String określający którą listę pobrać
   * @return plik pdf z listą rekordów danego typu
   */
  @GetMapping
  public ResponseEntity<?> getListToPdf(@RequestParam(value = "type") String type) {
    return ResponseEntity.ok(exportService.getPdf(type));
  }
}
