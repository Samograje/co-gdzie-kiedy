package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareInputDTO;
import org.polsl.backend.dto.software.SoftwareOutputDTO;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.key.ComputerSetSoftwareKey;
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
  private SoftwareRepository softwareRepository;
  private ComputerSetRepository computerSetRepository;
  private ComputerSetSoftwareRepository computerSetSoftwareRepository;

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
      dto.setName(software.getName());
      dto.setId(software.getId());
      softwareOutputDTO.add(dto);
    }

    PaginatedResult<SoftwareOutputDTO> response = new PaginatedResult<>();
    response.setItems(softwareOutputDTO);
    response.setTotalElements((long) softwareOutputDTO.size());
    return response;
  }

  @Transactional
  public void createSoftware(SoftwareInputDTO request) {
    Software newSoftware = new Software();
    newSoftware.setName(request.getName());
    softwareRepository.save(newSoftware);
    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if(computerSetIdsSet != null)
    {
      for (Long id : computerSetIdsSet) {
        ComputerSetSoftwareKey key = new ComputerSetSoftwareKey();
        key.setSoftwareId(newSoftware.getId());
        key.setComputerSetId(computerSetRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Zestaw komputerowy", "id", id)).getId());
        key.setValidFrom(LocalDateTime.now());
        ComputerSetSoftware newComputerSetSoftware = new ComputerSetSoftware();
        newComputerSetSoftware.setId(key);
        newComputerSetSoftware.setValidTo(null);
        computerSetSoftwareRepository.save(newComputerSetSoftware);
      }
    }
  }

  @Transactional
  public void editSoftware(Long id, SoftwareInputDTO request) throws NotFoundException {
    //Edit name
    Software software = softwareRepository.findById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    software.setName(request.getName());
    softwareRepository.save(software);

    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if(computerSetIdsSet != null)
    {
      Set<ComputerSetSoftware> computerSetSoftwareSet = computerSetSoftwareRepository.findAllBySoftwareId(id);
      //Old record
      for(ComputerSetSoftware computerSetSoftware : computerSetSoftwareSet)
      {
        computerSetSoftware.setValidTo(LocalDateTime.now());
        computerSetSoftwareRepository.save(computerSetSoftware);
      }

      //New record
      for (Long computerSetId : computerSetIdsSet) {
        ComputerSetSoftwareKey key = new ComputerSetSoftwareKey();
        key.setSoftwareId(id);
        key.setComputerSetId(computerSetId);
        key.setValidFrom(LocalDateTime.now());
        ComputerSetSoftware newComputerSetSoftware = new ComputerSetSoftware();
        newComputerSetSoftware.setId(key);
        newComputerSetSoftware.setValidTo(null);
        computerSetSoftwareRepository.save(newComputerSetSoftware);
      }
    }
  }

  public void deleteSoftware(Long id) {
    softwareRepository.findAllById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    softwareRepository.deleteById(id);
  }
}
