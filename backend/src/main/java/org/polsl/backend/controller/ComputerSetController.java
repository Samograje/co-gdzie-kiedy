package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetDTO;
import org.polsl.backend.dto.computerset.ComputerSetListOutputDTO;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.service.ComputerSetService;
import org.polsl.backend.service.export.ExportService;
import org.polsl.backend.specification.ComputerSetSpecification;
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
 * Kontroler odpowiedzialny za zarządzanie zestawami komputerowymi.
 */
@RestController
@RequestMapping("api/computer-sets")
public class ComputerSetController {
  private final ComputerSetService computerSetService;
  private final ExportService exportService;

  @Autowired
  public ComputerSetController(ComputerSetService computerSetService, ExportService exportService) {
    this.computerSetService = computerSetService;
    this.exportService = exportService;
  }

  /**
   * Endpoint obsługujący uzyskiwanie listy zestawów komputerowych.
   *
   * @param searchQuery kryteria wyszukiwania
   * @param searchType  typ wyszukiwania
   * @param withHistory informacja o tym, czy należy wyświetlić również usunięte rekordy
   * @return lista zestawów komputerowych
   */
  @GetMapping
  public ResponseEntity<?> getAllComputerSets(
      @RequestParam(value = "search", required = false) String searchQuery,
      @RequestParam(value = "search-type", required = false) String searchType,
      @RequestParam(name = "with-history", required = false, defaultValue = "false") boolean withHistory
  ) {
    Specification<ComputerSet> specification = getSpecification(searchQuery, searchType, ComputerSetSpecification.class);
    return ResponseEntity.ok(computerSetService.getAllComputerSets(specification, withHistory));
  }

  /**
   * Endpoint obsługujący uzyskiwanie pliku Pdf z listą zestawów komputerowych.
   *
   * @return plik pdf z listą rekordów
   */
  @GetMapping("/export")
  public ResponseEntity<?> printListToPdf() {
    PaginatedResult<ComputerSetListOutputDTO> data = computerSetService.getAllComputerSets(null, false);
    InputStreamResource inputStreamResource = exportService.export("Zestawy komputerowe", data.getItems());
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(inputStreamResource);
  }

  /**
   * Endpoint obsługujący dodawanie nowego zestawu komputerowego.
   *
   * @param request stuktura {@link ComputerSetDTO} zawierająca dane nowego zestawu komputerowego
   * @return informacja o poprawnym utworzeniu zestawu komputerowego
   */
  @PostMapping
  public ResponseEntity<?> createComputerSet(@Valid @RequestBody ComputerSetDTO request) {
    computerSetService.createComputerSet(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono Zestaw komputerowy"));
  }

  /**
   * Endpoint obsługujący edycję parametrów zestawu komputerowego.
   *
   * @param request stuktura {@link ComputerSetDTO} zawierająca nowe dane zestawu komputerowego
   * @return informacja o poprawnym zaktualizowaniu parametrów zestawu komputerowego
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editComputerSet(
      @PathVariable(value = "id") Long id,
      @Valid @RequestBody ComputerSetDTO request
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

  /**
   * Endpoint obsługujący uzyskiwanie zestawu komputerowego o danym id.
   *
   * @param id ID wybranego zestawu komputerowego
   * @return zestaw komputerowy o danym id
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getComputerSet(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(computerSetService.getOneComputerSet(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie historii oprogramowania zestawu komputerowego o podanym id.
   *
   * @param id ID zestawu komputerowego
   * @return historia oprogramowania dla zestawu komputerowego
   */
  @GetMapping("/{id}/software-history")
  public ResponseEntity<?> getComputerSetSoftwareHistory(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(computerSetService.getComputerSetSoftwareHistory(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie historii sprzętów należących do zestawu komputerowego o podanym id.
   *
   * @param id ID zestawu komputerowego
   * @return historia sprzętu dla zestawu komputerowego
   */
  @GetMapping("/{id}/hardware-history")
  public ResponseEntity<?> getComputerSetHardwareHistory(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(computerSetService.getComputerSetHardwareHistory(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie historii osób i miejsc przypisanych do zestawu komputerowego o podanym id.
   *
   * @param id ID zestawu komputerowego
   * @return historia osób i miejsc dla zestawu komputerowego
   */
  @GetMapping("/{id}/affiliations-history")
  public ResponseEntity<?> getComputerSetAffiliationsHistory(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(computerSetService.getComputerSetAffiliationsHistory(id));
  }
}
