package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.affiliation.AffiliationInputDTO;
import org.polsl.backend.dto.affiliation.AffiliationOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.repository.AffiliationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Logika biznesowa przeznaczeń.
 */
@Service
public class AffiliationService {
  private AffiliationRepository affiliationRepository;

  @Autowired
  public AffiliationService(AffiliationRepository affiliationRepository) {
    this.affiliationRepository = affiliationRepository;
  }

  public PaginatedResult<AffiliationOutputDTO> getAllAffiliations() {
    Iterable<Affiliation> affiliations = affiliationRepository.findAll();
    List<AffiliationOutputDTO> dtos = new ArrayList<>();
    for (Affiliation affiliation : affiliations) {
      if (!affiliation.getDeleted()) {
        AffiliationOutputDTO dto = new AffiliationOutputDTO();
        StringBuilder stringBuilder = new StringBuilder();
        boolean isSeparatorNeeded = false;
        if (!Objects.equals(affiliation.getFirstName(), "")) {
          stringBuilder.append(affiliation.getFirstName());
          isSeparatorNeeded = true;
        }
        if (!Objects.equals(affiliation.getLastName(), "")) {
          if (isSeparatorNeeded) {
            stringBuilder.append(" ");
          }
          stringBuilder.append(affiliation.getLastName());
          isSeparatorNeeded = true;
        }
        if (!Objects.equals(affiliation.getLocation(), "")) {
          if (isSeparatorNeeded) {
            stringBuilder.append(" - ");
          }
          stringBuilder.append(affiliation.getLocation());
        }
        dto.setName(stringBuilder.toString());
        dtos.add(dto);
      }
    }
    PaginatedResult<AffiliationOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public void createAffiliation(AffiliationInputDTO request) {
    Affiliation affiliation = new Affiliation();
    affiliation.setFirstName(request.getFirstName());
    affiliation.setLastName(request.getLastName());
    affiliation.setLocation(request.getLocation());
    affiliationRepository.save(affiliation);
  }

  public void editAffiliation(Long id, AffiliationInputDTO request) {
    Affiliation affiliation = affiliationRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Nie znaleziono przeznaczenia o podanym ID"));
    affiliation.setFirstName(request.getFirstName());
    affiliation.setLastName(request.getLastName());
    affiliation.setLocation(request.getLocation());
    affiliationRepository.save(affiliation);
  }

  public void deleteAffiliation(Long id) {
    Affiliation affiliation = affiliationRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Nie znaleziono przeznaczenia o podanym ID"));
    affiliation.setDeleted(true);
  }
}
