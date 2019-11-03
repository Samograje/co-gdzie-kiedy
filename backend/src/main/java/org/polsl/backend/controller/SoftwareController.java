package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.software.SoftwareInputDTO;
import org.polsl.backend.service.SoftwareService;
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

  /**
   * Endpoint obsługujący dodawanie nowego oprogramowania.
   *
   * @param request struktura {@link SoftwareInputDTO} zawierająca dane nowego oprogramwoania
   * @return informacja o poprawnym utworzeniu oprogramowaniu
   */
  @PostMapping
  public ResponseEntity<?> createSoftware(@Valid @RequestBody SoftwareInputDTO request) {
    softwareService.createSoftware(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono oprogramowanie."));
  }

  /**
   * Endpoint obsługujący edycję parametrów oprogramowania.
   *
   * @param request stuktura {@link SoftwareInputDTO} zawierająca nowe dane oprogramowania
   * @return informacja o poprawnym zaktualizowaniu parametrów oprogramowania
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editSoftware(
      @PathVariable(value = "id") Long id,
      @Valid @RequestBody SoftwareInputDTO request
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
