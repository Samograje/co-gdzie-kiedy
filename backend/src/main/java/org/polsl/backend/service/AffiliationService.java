package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.affiliation.AffiliationDTO;
import org.polsl.backend.dto.affiliation.AffiliationListOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Logika biznesowa przynależności.
 */
@Service
public class AffiliationService {
  private AffiliationRepository affiliationRepository;

  @Autowired
  public AffiliationService(AffiliationRepository affiliationRepository) {
    this.affiliationRepository = affiliationRepository;
  }

  static String generateName(Affiliation affiliation) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean isSeparatorNeeded = false;
    if (affiliation.getFirstName() != null && affiliation.getFirstName().length() > 0) {
      stringBuilder.append(affiliation.getFirstName());
      isSeparatorNeeded = true;
    }
    if (affiliation.getLastName() != null && affiliation.getLastName().length() > 0) {
      if (isSeparatorNeeded) {
        stringBuilder.append(" ");
      }
      stringBuilder.append(affiliation.getLastName());
      isSeparatorNeeded = true;
    }
    if (affiliation.getLocation() != null && affiliation.getLocation().length() > 0) {
      if (isSeparatorNeeded) {
        stringBuilder.append(" - ");
      }
      stringBuilder.append(affiliation.getLocation());
    }
    return stringBuilder.toString();
  }

  public PaginatedResult<AffiliationListOutputDTO> getAffiliations(Specification<Affiliation> specification) {
    final Specification<Affiliation> resultSpecification = (
      (Specification<Affiliation>) (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"))
    ).and(specification);

    Iterable<Affiliation> affiliations = affiliationRepository.findAll(resultSpecification);

    List<AffiliationListOutputDTO> dtos = new ArrayList<>();
    affiliations.forEach(affiliation -> dtos.add(toListOutputDTO(affiliation)));

    PaginatedResult<AffiliationListOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public AffiliationDTO getAffiliation(Long id) throws NotFoundException {
    Affiliation affiliation = affiliationRepository.findById(id)
      .orElseThrow(() -> new NotFoundException("przynależność", "id", id));

    AffiliationDTO response = new AffiliationDTO();
    response.setFirstName(affiliation.getFirstName());
    response.setLastName(affiliation.getLastName());
    response.setLocation(affiliation.getLocation());
    return response;
  }

  public void createAffiliation(AffiliationDTO request) {
    Affiliation affiliation = new Affiliation();
    affiliation.setFirstName(request.getFirstName());
    affiliation.setLastName(request.getLastName());
    affiliation.setLocation(request.getLocation());
    affiliationRepository.save(affiliation);
  }

  public void editAffiliation(Long id, AffiliationDTO request) throws NotFoundException {
    Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(id)
      .orElseThrow(() -> new NotFoundException("przynależność", "id", id));
    affiliation.setFirstName(request.getFirstName());
    affiliation.setLastName(request.getLastName());
    affiliation.setLocation(request.getLocation());
    affiliationRepository.save(affiliation);
  }

  public void deleteAffiliation(Long id) throws NotFoundException {
    Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(id)
      .orElseThrow(() -> new NotFoundException("przynależność", "id", id));
    affiliation.setDeleted(true);
    affiliationRepository.save(affiliation);
  }

  private AffiliationListOutputDTO toListOutputDTO(Affiliation affiliation) {
    AffiliationListOutputDTO dto = new AffiliationListOutputDTO();

    dto.setId(affiliation.getId());
    dto.setFirstName(affiliation.getFirstName());
    dto.setLastName(affiliation.getLastName());
    dto.setLocation(affiliation.getLocation());

    Set<String> computerSetsInventoryNumbers = affiliation.getAffiliationComputerSetSet()
      .stream()
      .filter(affiliationComputerSet -> affiliationComputerSet.getValidTo() == null)
      .map(affiliationComputerSet -> affiliationComputerSet.getComputerSet().getInventoryNumber())
      .collect(Collectors.toSet());
    dto.setComputerSetsInventoryNumbers(computerSetsInventoryNumbers);

    Set<String> hardwareInventoryNumbers = affiliation.getAffiliationHardwareSet()
      .stream()
      .filter(affiliationHardware -> affiliationHardware.getValidTo() == null)
      .map(affiliationHardware -> affiliationHardware.getHardware().getInventoryNumber())
      .collect(Collectors.toSet());
    dto.setHardwareInventoryNumbers(hardwareInventoryNumbers);

    return dto;
  }
}
