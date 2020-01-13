package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.affiliation.AffiliationDTO;
import org.polsl.backend.dto.affiliation.AffiliationListOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

  public PaginatedResult<AffiliationListOutputDTO> getAffiliations() {
    Iterable<Affiliation> affiliations = affiliationRepository.findAllByIsDeletedIsFalse();
    List<AffiliationListOutputDTO> dtos = new ArrayList<>();
    for (Affiliation affiliation : affiliations) {
      AffiliationListOutputDTO dto = new AffiliationListOutputDTO();
      dto.setId(affiliation.getId());
      dto.setName(generateName(affiliation));
      dtos.add(dto);
    }
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
}
