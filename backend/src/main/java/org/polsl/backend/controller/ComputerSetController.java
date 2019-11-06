package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.service.ComputerSetService;
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
 * Kontroler odpowiedzialny za zarządzanie zestawami komputerowymi.
 */
@RestController
@RequestMapping("api/computer-sets")
public class ComputerSetController {
  private final ComputerSetService computerSetService;

  @Autowired
  public ComputerSetController(ComputerSetService computerSetService) {
    this.computerSetService = computerSetService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy zestawów komputerowych.
   *
   * @return lista zestawów komputerowych
   */
  @GetMapping
  public ResponseEntity<?> getAllComputerSets() {
    return ResponseEntity.ok(computerSetService.getAllComputerSets());
  }

  /**
   * Endpoint obsługujący dodawanie nowego zestawu komputerowego.
   *
   * @param request stuktura {@link ComputerSetInputDTO} zawierająca dane nowego zestawu komputerowego
   * @return informacja o poprawnym utworzeniu zestawu komputerowego
   */
  @PostMapping
  public ResponseEntity<?> createComputerSet(@Valid @RequestBody ComputerSetInputDTO request) {
    computerSetService.createComputerSet(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono Zestaw komputerowy"));
  }

  /**
   * Endpoint obsługujący edycję parametrów zestawu komputerowego.
   *
   * @param request stuktura {@link ComputerSetInputDTO} zawierająca nowe dane zestawu komputerowego
   * @return informacja o poprawnym zaktualizowaniu parametrów zestawu komputerowego
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editComputerSet(
          @PathVariable(value = "id") Long id,
          @Valid @RequestBody ComputerSetInputDTO request
  ) {
    computerSetService.editComputerSet(id, request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Zaktualizowano parametry zestawu komputerowego"));
  }

  /**
   * Endpoint obsługujący usuwanie zestawu komputerowego.
   *
   * @param id ID zestawu komputerowego
   * @return informacja o poprawnym usunięciu zestawu komputerowego
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteComputerSet(@PathVariable(value = "id") Long id) {
    computerSetService.deleteComputerSet(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto zestaw komputerowy"));
  }
}
