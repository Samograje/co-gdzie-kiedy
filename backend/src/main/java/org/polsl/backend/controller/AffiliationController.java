package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.affiliation.AffiliationDTO;
import org.polsl.backend.dto.affiliation.AffiliationListOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.service.AffiliationService;
import org.polsl.backend.service.export.ExportService;
import org.polsl.backend.specification.AffiliationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Kontroler odpowiedzialny za zarządzanie przynależnościami.
 */
@RestController
@RequestMapping("api/affiliations")
public class AffiliationController {
  private final AffiliationService affiliationService;
  private final ExportService exportService;

  @Autowired
  public AffiliationController(AffiliationService affiliationService, ExportService exportService) {
    this.affiliationService = affiliationService;
    this.exportService = exportService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy przynależności.
   *
   * @param searchQuery    kryteria wyszukiwania
   * @param searchOperator operator wyszukiwania
   * @param withHistory    informacja o tym, czy należy wyświetlić również usunięte rekordy
   * @return lista przynależności
   */
  @GetMapping
  public ResponseEntity<?> getAffiliations(
      @RequestParam(value = "search", required = false) String searchQuery,
      @RequestParam(value = "search-operator", required = false) String searchOperator,
      @RequestParam(name = "with-history", required = false, defaultValue = "false") boolean withHistory
  ) {
    Specification<Affiliation> specification = getSpecification(searchQuery, searchOperator, AffiliationSpecification.class);
    return ResponseEntity.ok(affiliationService.getAffiliations(specification, withHistory));
  }

  /**
   * Endpoint obsługujący uzyskiwanie pojedynczej przynależności.
   *
   * @param id ID przynależności
   * @return struktura {@link AffiliationDTO} zawierająca dane przynależności o podanym ID
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getAffiliation(@PathVariable("id") Long id) {
    return ResponseEntity.ok(affiliationService.getAffiliation(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie pliku PDF z listą przynależności.
   *
   * @return plik PDF z listą rekordów
   */
  @GetMapping("/export")
  public ResponseEntity<?> printListToPdf() {
    PaginatedResult<AffiliationListOutputDTO> data = affiliationService.getAffiliations(null, false);
    InputStreamResource inputStreamResource = exportService.export("Osoby i miejsca", data.getItems());
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(inputStreamResource);
  }

  /**
   * Endpoint obsługujący dodawanie nowej przynależności.
   *
   * @param request stuktura {@link AffiliationDTO} zawierająca dane nowej przynależności
   * @return informacja o poprawnym utworzeniu przynależności
   */
  @PostMapping
  public ResponseEntity<?> createAffiliation(@Valid @RequestBody AffiliationDTO request) {
    affiliationService.createAffiliation(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono przynależność"));
  }

  /**
   * Endpoint obsługujący edycję parametrów przynależności.
   *
   * @param request stuktura {@link AffiliationDTO} zawierająca nowe dane przynależności
   * @return informacja o poprawnym zaktualizowaniu parametrów przynależności
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editAffiliation(@PathVariable("id") Long id, @Valid @RequestBody AffiliationDTO request) {
    affiliationService.editAffiliation(id, request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Zaktualizowano parametry przynależności"));
  }

  /**
   * Endpoint obsługujący usuwanie przynależności.
   *
   * @param id ID wybranej przynależności
   * @return informacja o poprawnym usunięciu przynależności
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAffiliation(@PathVariable("id") Long id) {
    affiliationService.deleteAffiliation(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto przynależność"));
  }
}
