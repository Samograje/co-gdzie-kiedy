package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationComputerSetRepository;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Logika biznesowa zestawów komputerowych.
 */
@Service
public class ComputerSetService {
  private final ComputerSetRepository computerSetRepository;
  private final AffiliationComputerSetRepository affiliationComputerSetRepository;
  private final ComputerSetHardwareRepository computerSetHardwareRepository;
  private final ComputerSetSoftwareRepository computerSetSoftwareRepository;
  private final AffiliationRepository affiliationRepository;
  private final HardwareRepository hardwareRepository;
  private final SoftwareRepository softwareRepository;

  @Autowired
  public ComputerSetService(ComputerSetRepository computerSetRepository,
                            AffiliationComputerSetRepository affiliationComputerSetRepository,
                            ComputerSetHardwareRepository computerSetHardwareRepository,
                            ComputerSetSoftwareRepository computerSetSoftwareRepository,
                            AffiliationRepository affiliationRepository,
                            HardwareRepository hardwareRepository,
                            SoftwareRepository softwareRepository) {
    this.computerSetRepository = computerSetRepository;
    this.affiliationComputerSetRepository = affiliationComputerSetRepository;
    this.computerSetHardwareRepository = computerSetHardwareRepository;
    this.computerSetSoftwareRepository = computerSetSoftwareRepository;
    this.affiliationRepository = affiliationRepository;
    this.hardwareRepository = hardwareRepository;
    this.softwareRepository = softwareRepository;
  }

  public PaginatedResult<ComputerSetOutputDTO> getAllComputerSets() {
    Iterable<ComputerSet> computerSets = computerSetRepository.findAll();
    List<ComputerSetOutputDTO> dtos = new ArrayList<>();
    for (ComputerSet computerSet : computerSets) {
      ComputerSetOutputDTO dto = new ComputerSetOutputDTO();
      dto.setId(computerSet.getId());
      dto.setName(computerSet.getName());
      dtos.add(dto);
    }
    PaginatedResult<ComputerSetOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  @Transactional
  public void createComputerSet(ComputerSetInputDTO request) {
    ComputerSet computerSet = new ComputerSet();
    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    // TODO: błąd: wykonało akcję zamiast rzucić wyjątek
    //  sytuacja: podanie nieprawidłowego affiliationId (usuniętego)
    //ok

    Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(request.getAffiliationId())
        .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
    AffiliationComputerSet affiliationComputerSet = new AffiliationComputerSet(affiliation, computerSet);
    affiliationComputerSetRepository.save(affiliationComputerSet);

    // TODO: błąd: wykonało akcję zamiast rzucić wyjątek
    //  sytuacja: podanie hardwareIds: [8], gdzie hardware o ID = 8 jest usunięty
    // ok

    if (request.getHardwareIds() != null) {
      request.getHardwareIds().forEach(hardwareId -> {
        Hardware hardware = hardwareRepository.findByIdAndValidToIsNull(hardwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", hardwareId));
        ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
        computerSetHardwareRepository.save(computerSetHardware);
      });
    }

    if (request.getSoftwareIds() != null) {
      request.getSoftwareIds().forEach(softwareId -> {
        Software software = softwareRepository.findById(softwareId)
                .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", softwareId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  @Transactional
  public void editComputerSet(Long id, ComputerSetInputDTO request) throws NotFoundException {
    ComputerSet computerSet = computerSetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id));
    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    Long requestAffiliationId = request.getAffiliationId();
    Set<Long> requestHardwareIds = request.getHardwareIds();
    Set<Long> requestSoftwareIds = request.getSoftwareIds();

    Set<AffiliationComputerSet> currentAffiliationComputerSetSet = computerSet.getAffiliationComputerSetSet();
    Set<ComputerSetHardware> currentComputerSetHardwareSet = computerSet.getComputerSetHardwareSet();
    Set<ComputerSetSoftware> currentComputerSetSoftwareSet = computerSet.getComputerSetSoftwareSet();

    // TODO: wyświetliło komunikat o sukcesie zamiast komunikatu o błędzie, do tego ustawiło valid_to
    //  sytuacja: podanie nieprawidłowego affiliationId (nieistniejącego)

    // TODO: wyświetliło komunikat o sukcesie zamiast komunikatu o błędzie, do tego ustawiło valid_to
    //  sytuacja: podanie nieprawidłowego affiliationId (usuniętego)

    // TODO: nie utworzyło nowego rekordu w bazie
    //  sytuacja: zmiana affiliationId z prawidłowego na prawidłowe

    //-----------AFFILIATION---------------
    currentAffiliationComputerSetSet.forEach(currentAffiliationComputerSet -> {
      // jeżeli połączenie jest aktualne
      if (currentAffiliationComputerSet.getValidTo() == null) {

        Long currentAffiliationId = currentAffiliationComputerSet.getAffiliation().getId();
        if (currentAffiliationId != requestAffiliationId) {

          Affiliation newAffiliation = affiliationRepository.findById(requestAffiliationId)
                  .orElseThrow(() -> new NotFoundException("afiliacja", "id", requestAffiliationId));

          AffiliationComputerSet newAffiliationComputerSet = new AffiliationComputerSet(newAffiliation, computerSet);
          computerSet.getAffiliationComputerSetSet().add(newAffiliationComputerSet);

          currentAffiliationComputerSet.setValidTo(LocalDateTime.now());
        }
      }
    });

    // w testach podawałem zawsze softwareIds: [], może to było problemem, nie wiem

    // TODO: null pointer exception
    //  sytuacja: hardwareIds będący nullem

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [] na [0], gdzie nie istnieje hardware o ID = 0

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [] na [8], gdzie hardware o ID = 8 jest usunięty

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [] na [10], gdzie hardware o ID = 10 istnieje i nie jest usunięty

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [10] na [14], gdzie oba hardware'y istnieją i nie są usunięte

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [10] na [8], gdzie hardware o ID = 8 jest usunięty

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [10] na [0], gdzie hardware o ID = 0 nie istnieje

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [10] na [10, 14], gdzie oba hardware'y istnieją i nie są usunięte

    // TODO: null pointer exception
    //  sytuacja: zmiana hardwareIds z [10] na [], gdzie hardware o ID = 10 istnieje i nie jest usunięty

    //--------------HARDWARE-------------
    //jeżeli połączenie staje się nieaktualne
    Set<Long> currentHardwareIds = null;
    currentComputerSetHardwareSet.forEach(currentComputerSetHardware -> {
      if (currentComputerSetHardware.getValidTo() == null) {
        //aktualne id sprzętów należących do zestawów komputerowych
        currentHardwareIds.add(currentComputerSetHardware.getHardware().getId());

        if (!requestHardwareIds.contains(currentComputerSetHardware.getHardware())) {
          currentComputerSetHardware.setValidTo(LocalDateTime.now());
        }
      }
    });

    requestHardwareIds.forEach(requestHardwareId -> {
      // jeżeli nie było do tej pory takiego sprzętu lub lista sprzętów jest pusta
      if (!currentHardwareIds.contains(requestHardwareId) || currentHardwareIds == null) {
        //dodaje nowe połącznie
        Hardware newHardware = hardwareRepository.findById(requestHardwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", requestHardwareId));
        ComputerSetHardware newComputerSetHardware = new ComputerSetHardware(computerSet, newHardware);
        computerSet.getComputerSetHardwareSet().add(newComputerSetHardware);
      }
    });

    //--------------SOFTWARE-------------
    Set<Long> currentSoftwareIds = null;
    currentComputerSetSoftwareSet.forEach(computerSetSoftware -> {
      if (computerSetSoftware.getValidTo() == null) {
        currentSoftwareIds.add(computerSetSoftware.getSoftware().getId());
        if (!requestSoftwareIds.contains(computerSetSoftware.getSoftware())) {
          computerSetSoftware.setValidTo(LocalDateTime.now());
        }
      }
    });

    requestHardwareIds.forEach(requestSoftwareId -> {
      // jeżeli nie było do tej pory takiego sprzętu lub lista sprzętów jest pusta
      if (!currentSoftwareIds.contains(requestSoftwareId) || currentSoftwareIds == null) {
        //dodaje nowe połącznie
        Software newSoftware = softwareRepository.findById(requestSoftwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", requestSoftwareId));
        ComputerSetSoftware newComputerSetSoftware = new ComputerSetSoftware(computerSet, newSoftware);
        computerSet.getComputerSetSoftwareSet().add(newComputerSetSoftware);
      }
    });
  }

  public void deleteComputerSet(Long id) throws NotFoundException {
    ComputerSet computerSet = computerSetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id));
    computerSet.setValidTo(LocalDateTime.now());
    computerSetRepository.save(computerSet);

    affiliationComputerSetRepository.findAllByComputerSetIdAndValidToIsNull(computerSet.getId()).forEach(relation -> {
      relation.setValidTo(LocalDateTime.now());
      affiliationComputerSetRepository.save(relation);
    });

    computerSetHardwareRepository.findAllByComputerSetIdAndValidToIsNull(computerSet.getId()).forEach(relation -> {
      relation.setValidTo(LocalDateTime.now());
      computerSetHardwareRepository.save(relation);
    });

    computerSetSoftwareRepository.findAllBySoftwareIdAndValidToIsNull(computerSet.getId()).forEach(relation -> {
      relation.setValidTo(LocalDateTime.now());
      computerSetSoftwareRepository.save(relation);
    });
  }

}
