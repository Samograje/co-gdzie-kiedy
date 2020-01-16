package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareDTO;
import org.polsl.backend.dto.software.SoftwareListOutputDTO;
import org.polsl.backend.entity.Software;
import org.polsl.backend.filtering.Search;
import org.polsl.backend.service.ExportService;
import org.polsl.backend.service.SoftwareService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


/**
 * Kontroler odpowiedzialny za zarządzanie oprogramowaniem.
 */
@RestController
@RequestMapping("api/software")
public class SoftwareController {
  private final SoftwareService softwareService;
  private final ExportService exportService;

  public SoftwareController(SoftwareService softwareService, ExportService exportService) {
    this.softwareService = softwareService;
    this.exportService = exportService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy oprogramowania.
   *
   * @return lista oprogramowania
   */
  @GetMapping
  public ResponseEntity<?> getAllSoftware(@RequestParam(value = "search", required = false) String search) {
    Search<Software> filtering = new Search<>(new Software(), search);
    return ResponseEntity.ok(softwareService.getAllSoftware(filtering.searchInitialization()));
  }

  /**
   * Endpoint obsługujący uzyskiwanie oprogramowania o danym id.
   *
   * @param id ID wybranego oprogramowania
   * @return oprogramowanie o danym id
   */

  @GetMapping("/{id}")
  public ResponseEntity<?> getSoftware(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(softwareService.getOneSoftware(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie historii powiązań oprogramowania o danym id z zestawami komputerowymi.
   *
   * @param id ID wybranego oprogramowania
   * @return historia powiązań oprogramowania o danym id z zestawami komputerowymi
   */
  @GetMapping("/{id}/computer-sets-history")
  public ResponseEntity<?> getHardwareComputerSetsHistoryList(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(softwareService.getSoftwareComputerSetsHistory(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie pliku Pdf z listą oprogramowania.
   *
   * @return plik pdf z listą rekordów
   */
  @GetMapping("/export")
  public ResponseEntity<?> printListToPdf(@RequestParam(value = "search", required = false) String search) {
    Search<Software> filtering = new Search<>(new Software(), search);
    PaginatedResult<SoftwareListOutputDTO> data = softwareService.getAllSoftware(filtering.searchInitialization());
    InputStreamResource inputStreamResource = exportService.export("software", data.getItems());

    return new ResponseEntity<>(inputStreamResource, exportService.getHttpHeaders(), HttpStatus.OK);
  }
  //TODO: PDF

  /**
   * Endpoint obsługujący dodawanie nowego oprogramowania.
   *
   * @param request struktura {@link SoftwareDTO} zawierająca dane nowego oprogramwoania
   * @return informacja o poprawnym utworzeniu oprogramowaniu
   */
  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping
  public ResponseEntity<?> createSoftware(@Valid @RequestBody SoftwareDTO request) {
    softwareService.createSoftware(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono oprogramowanie."));
  }

  /**
   * Endpoint obsługujący edycję parametrów oprogramowania.
   *
   * @param request stuktura {@link SoftwareDTO} zawierająca nowe dane oprogramowania
   * @return informacja o poprawnym zaktualizowaniu parametrów oprogramowania
   */
  @PutMapping("/{id}")
  @CrossOrigin(origins = "http://localhost:3000")
  public ResponseEntity<?> editSoftware(
      @PathVariable(value = "id") Long id,
      @Valid @RequestBody SoftwareDTO request
  ) {
    softwareService.editSoftware(id, request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Zaktualizowano parametry oprogramowania."));
  }

  /**
   * Endpoint obsługujący usuwanie oprogramowania.
   *
   * @param id ID wybranego oprogramowania
   * @return informacja o poprawnym usunięciu oprogramowania
   */
  @CrossOrigin(origins = "http://localhost:3000")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteSoftware(@PathVariable(value = "id") Long id) {
    softwareService.deleteSoftware(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto oprogramowanie."));
  }
}
