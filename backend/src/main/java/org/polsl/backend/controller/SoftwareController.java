package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareDTO;
import org.polsl.backend.dto.software.SoftwareListOutputDTO;
import org.polsl.backend.entity.Software;
import org.polsl.backend.service.SoftwareService;
import org.polsl.backend.service.export.ExportService;
import org.polsl.backend.specification.SoftwareSpecification;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.polsl.backend.service.filtering.SearchService.getSpecification;

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
   * @param searchQuery    kryteria wyszukiwania
   * @param searchOperator operator wyszukiwania
   * @param withHistory    informacja o tym, czy należy wyświetlić również usunięte rekordy
   * @return lista oprogramowania
   */
  @GetMapping
  public ResponseEntity<?> getAllSoftware(
      @RequestParam(value = "search", required = false) String searchQuery,
      @RequestParam(value = "search-operator", required = false) String searchOperator,
      @RequestParam(name = "with-history", required = false, defaultValue = "false") boolean withHistory
  ) {
    Specification<Software> specification = getSpecification(searchQuery, searchOperator, SoftwareSpecification.class);
    return ResponseEntity.ok(softwareService.getAllSoftware(specification, withHistory));
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
    PaginatedResult<SoftwareListOutputDTO> data = softwareService.getAllSoftware(null, false);
    InputStreamResource inputStreamResource = exportService.export("Oprogramowanie", data.getItems());
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(inputStreamResource);
  }

  /**
   * Endpoint obsługujący dodawanie nowego oprogramowania.
   *
   * @param request struktura {@link SoftwareDTO} zawierająca dane nowego oprogramwoania
   * @return informacja o poprawnym utworzeniu oprogramowaniu
   */
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
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteSoftware(@PathVariable(value = "id") Long id) {
    softwareService.deleteSoftware(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto oprogramowanie."));
  }
}
