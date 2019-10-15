package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.affiliation.AffiliationInputDTO;
import org.polsl.backend.service.AffiliationService;
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
 * Kontroler odpowiedzialny za zarządzanie przeznaczeniami.
 */
@RestController
@RequestMapping("api/affiliations")
public class AffiliationController {
  private final AffiliationService affiliationService;

  @Autowired
  public AffiliationController(AffiliationService affiliationService) {
    this.affiliationService = affiliationService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy przeznaczeń.
   *
   * @return lista przeznaczeń
   */
  @GetMapping
  public ResponseEntity<?> getAllAffiliations() {
    return ResponseEntity.ok(affiliationService.getAllAffiliations());
  }

  /**
   * Endpoint obsługujący dodawanie nowego przeznaczenia.
   *
   * @param request stuktura {@link AffiliationInputDTO} zawierająca dane nowego przeznaczenia
   * @return informacja o poprawnym utworzeniu przeznaczenia
   */
  @PostMapping
  public ResponseEntity<?> createAffiliation(@Valid @RequestBody AffiliationInputDTO request) {
    affiliationService.createAffiliation(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono przeznaczenie"));
  }

  /**
   * Endpoint obsługujący edycję parametrów przeznaczenia.
   *
   * @param request stuktura {@link AffiliationInputDTO} zawierająca nowe dane przeznaczenia
   * @return informacja o poprawnym zaktualizowaniu parametrów przeznaczenia
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editAffiliation(
    @PathVariable(value = "id") Long id,
    @Valid @RequestBody AffiliationInputDTO request
  ) {
    affiliationService.editAffiliation(id, request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Zaktualizowano parametry przeznaczenia"));
  }

  /**
   * Endpoint obsługujący usuwanie przeznaczenia.
   *
   * @param id ID wybranego przeznaczenia
   * @return informacja o poprawnym usunięciu przeznaczenia
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAffiliation(@PathVariable(value = "id") Long id) {
    affiliationService.deleteAffiliation(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto przeznaczenie"));
  }
}
