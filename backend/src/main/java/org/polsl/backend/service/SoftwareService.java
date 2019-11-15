package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareDTO;
import org.polsl.backend.dto.software.SoftwareOutputDTO;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Logika biznesowa oprogramowania.
 */
@Service
public class SoftwareService {
  private final SoftwareRepository softwareRepository;
  private final ComputerSetRepository computerSetRepository;
  private final ComputerSetSoftwareRepository computerSetSoftwareRepository;

  @Autowired
  public SoftwareService(SoftwareRepository softwareRepository,
                         ComputerSetRepository computerSetRepository,
                         ComputerSetSoftwareRepository computerSetSoftwareRepository) {
    this.softwareRepository = softwareRepository;
    this.computerSetRepository = computerSetRepository;
    this.computerSetSoftwareRepository = computerSetSoftwareRepository;
  }

  public PaginatedResult<SoftwareOutputDTO> getAllSoftware() {
    Iterable<Software> softwares = softwareRepository.findAll();
    List<SoftwareOutputDTO> softwareOutputDTO = new ArrayList<>();
    for (Software software : softwares) {
      SoftwareOutputDTO dto = new SoftwareOutputDTO();
      dto.setId(software.getId());
      dto.setName(software.getName());
      softwareOutputDTO.add(dto);
    }

    PaginatedResult<SoftwareOutputDTO> response = new PaginatedResult<>();
    response.setItems(softwareOutputDTO);
    response.setTotalElements((long) softwareOutputDTO.size());
    return response;
  }

  public SoftwareDTO getOneSoftware(Long id) {
    Software software = softwareRepository.findByIdAndValidToIsNull(id)
        .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", id));
    SoftwareDTO dto = new SoftwareDTO();
    Set<ComputerSetSoftware> computerSetSoftwareSet = computerSetSoftwareRepository.findAllBySoftwareIdAndValidToIsNull(id);
    //Set of current computer sets for selected software
    Set<Long> ids = new HashSet<>();
    computerSetSoftwareSet.forEach(computerSetSoftware -> ids.add(computerSetSoftware.getComputerSet().getId()));
    dto.setName(software.getName());
    dto.setComputerSetIds(ids);
    return dto;
  }

  @Transactional
  public void createSoftware(SoftwareDTO request) {
    Software software = new Software();
    software.setName(request.getName());
    softwareRepository.save(software);

    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if (computerSetIdsSet != null) {
      computerSetIdsSet.forEach(computerSetId -> {
        // TODO: w momencie, gdy tabela zestawów komputerowych będzie miała kolumnę valid_to,
        //  trzeba będzie tutaj sprawdzać, czy zestaw komputerowy nie jest aby usunięty.
        ComputerSet computerSet = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  @Transactional
  public void editSoftware(Long id, SoftwareDTO request) throws NotFoundException {
    //Edit name
    Software software = softwareRepository.findById(id).orElseThrow(() -> new NotFoundException("oprogramowanie", "id", id));
    software.setName(request.getName());
    softwareRepository.save(software);
    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if (computerSetIdsSet != null) {
      Set<ComputerSetSoftware> computerSetSoftwareSet = computerSetSoftwareRepository.findAllBySoftwareIdAndValidToIsNull(id);
      //Delete id(s) of computerSets which schould not be changed or make history record(s)
      computerSetSoftwareSet.forEach(computerSetSoftware -> {
        if (computerSetIdsSet.contains(computerSetSoftware.getComputerSet().getId())) {
          computerSetIdsSet.remove(computerSetSoftware.getComputerSet().getId());
        } else {
          computerSetSoftware.setValidTo(LocalDateTime.now());
          computerSetSoftwareRepository.save(computerSetSoftware);
        }
      });
      //New record(s)
      computerSetIdsSet.forEach(computerSetId -> {
        // TODO: w momencie, gdy tabela zestawów komputerowych będzie miała kolumnę valid_to,
        //  trzeba będzie tutaj sprawdzać, czy zestaw komputerowy nie jest aby usunięty.
        ComputerSet computerSet = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  @Transactional
  public void deleteSoftware(Long id) {
    Software software = softwareRepository.findByIdAndValidToIsNull(id)
        .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", id));
    software.setValidTo(LocalDateTime.now());
    softwareRepository.save(software);
    Set<ComputerSetSoftware> computerSetSoftwareSet = computerSetSoftwareRepository.findAllBySoftwareIdAndValidToIsNull(id);
    computerSetSoftwareSet.forEach(computerSetSoftware -> {
      computerSetSoftware.setValidTo(LocalDateTime.now());
      computerSetSoftwareRepository.save(computerSetSoftware);
    });
  }
}
