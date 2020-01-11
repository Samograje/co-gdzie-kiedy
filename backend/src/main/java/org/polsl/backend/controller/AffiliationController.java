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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Kontroler odpowiedzialny za zarządzanie przynależnościami.
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
   * Endpoint obsługujący uzyskiwanie listy przynależności.
   *
   * @param withHistory informacja o tym, czy należy wyświetlić również usunięte rekordy
   * @return lista przynależności
   */
  @GetMapping
  public ResponseEntity<?> getAllAffiliations(
      @RequestParam(name = "with-history", required = false, defaultValue = "false") boolean withHistory
  ) {
    return ResponseEntity.ok(affiliationService.getAffiliations(withHistory));
  }

  /**
   * Endpoint obsługujący dodawanie nowej przynależności.
   *
   * @param request stuktura {@link AffiliationInputDTO} zawierająca dane nowej przynależności
   * @return informacja o poprawnym utworzeniu przynależności
   */
  @PostMapping
  public ResponseEntity<?> createAffiliation(@Valid @RequestBody AffiliationInputDTO request) {
    affiliationService.createAffiliation(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono przynależność"));
  }

  /**
   * Endpoint obsługujący edycję parametrów przynależności.
   *
   * @param request stuktura {@link AffiliationInputDTO} zawierająca nowe dane przynależności
   * @return informacja o poprawnym zaktualizowaniu parametrów przynależności
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editAffiliation(
      @PathVariable(value = "id") Long id,
      @Valid @RequestBody AffiliationInputDTO request
  ) {
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
  public ResponseEntity<?> deleteAffiliation(@PathVariable(value = "id") Long id) {
    affiliationService.deleteAffiliation(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto przynależność"));
  }
}
