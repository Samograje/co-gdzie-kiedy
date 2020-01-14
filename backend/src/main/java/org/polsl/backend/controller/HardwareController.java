package org.polsl.backend.controller;

import org.polsl.backend.dto.ApiBasicResponse;
import org.polsl.backend.dto.hardware.HardwareDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.filtering.Search;
import org.polsl.backend.service.HardwareService;
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
 * Kontroler odpowiedzialny za zarządzanie hardware'm.
 */
@RestController
@RequestMapping("api/hardware")
public class HardwareController {
  private final HardwareService hardwareService;

  @Autowired
  public HardwareController(HardwareService hardwareService) {
    this.hardwareService = hardwareService;
  }
  /**
   * Endpoint obsługujący uzyskiwanie listy wszystkich hardware'ów bądź niebędących składowymi żadnego zestawu komputerowego.
   *
   * @param soloOnly boolean informujący o tym, czy wyświetlać hardware niebędący składowm żadnego zestawu komputerowego
   * @return lista hardware'u według parametru
   */
  @GetMapping
  public ResponseEntity<?> getHardwareList(@RequestParam(name = "solo-only", required = false, defaultValue = "false") boolean soloOnly, @RequestParam(value="search", required=false) String search) {
    Search<Hardware> filtering = new Search<>(new Hardware(), search);
    return ResponseEntity.ok(hardwareService.getHardwareList(soloOnly, filtering.searchInitialization()));
  }

  /**
   * Endpoint obsługujący uzyskiwanie hardware'u o danym id.
   *
   * @param id ID wybranego sprzętu
   * @return hardware o danym id
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getHardware(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(hardwareService.getOneHardware(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie historii przynależności hardware'u o danym id.
   *
   * @param id ID wybranego sprzętu
   * @return historia przynależności hardware'u o danym id
   */
  @GetMapping("/{id}/affiliations-history")
  public ResponseEntity<?> getHardwareAffiliationsHistoryList(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(hardwareService.getHardwareAffiliationsHistory(id));
  }

  /**
   * Endpoint obsługujący uzyskiwanie historii powiązań hardware'u o danym id z zestawami komputerowymi.
   *
   * @param id ID wybranego sprzętu
   * @return historia powiązań hardware'u o danym id z zestawami komputerowymi
   */
  @GetMapping("/{id}/computer-sets-history")
  public ResponseEntity<?> getHardwareComputerSetsHistoryList(@PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(hardwareService.getHardwareComputerSetsHistory(id));
  }

  /**
   * Endpoint obsługujący dodawanie nowego sprzętu.
   *
   * @param request stuktura {@link HardwareDTO} zawierająca dane nowego sprzętu
   * @return informacja o poprawnym utworzeniu sprzętu
   */
  @PostMapping
  public ResponseEntity<?> createHardware(@Valid @RequestBody HardwareDTO request) {
    hardwareService.createHardware(request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Utworzono sprzęt"));
  }

  /**
   * Endpoint obsługujący edycję parametrów przynależności.
   *
   * @param request stuktura {@link HardwareDTO} zawierająca nowe dane sprzętu
   * @return informacja o poprawnym zaktualizowaniu parametrów sprzętu
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> editAffiliation(
      @PathVariable(value = "id") Long id,
      @Valid @RequestBody HardwareDTO request
  ) {
    hardwareService.editHardware(id, request);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Zaktualizowano parametry sprzętu"));
  }

  /**
   * Endpoint obsługujący usuwanie sprzętu.
   *
   * @param id ID wybranego sprzętu
   * @return informacja o poprawnym usunięciu sprzętu
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteHardware(@PathVariable(value = "id") Long id) {
    hardwareService.deleteHardware(id);
    return ResponseEntity.ok(new ApiBasicResponse(true, "Usunięto sprzęt"));
  }
}
