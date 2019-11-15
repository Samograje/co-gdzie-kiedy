package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareInputDTO;
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

  @Transactional
  public void createSoftware(SoftwareInputDTO request) {
    Software software = new Software();
    software.setName(request.getName());
    softwareRepository.save(software);

    //New record(s) in history
    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if (computerSetIdsSet != null) {
      computerSetIdsSet.forEach(computerSetId -> {

        ComputerSet computerSet = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
        //Check if computer set is not deleted before creating a new record in history
        if(computerSet.getValidTo() == null) {
          ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
          computerSetSoftwareRepository.save(computerSetSoftware);
        }
      });
    }
  }

  @Transactional
  public void editSoftware(Long id, SoftwareInputDTO request) throws NotFoundException {
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
      //New record(s) in history
      computerSetIdsSet.forEach(computerSetId -> {
        ComputerSet computerSet = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
        //Check if computer set is not deleted before creating a new record in history
        if(computerSet.getValidTo() == null) {
          ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
          computerSetSoftwareRepository.save(computerSetSoftware);
        }
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
