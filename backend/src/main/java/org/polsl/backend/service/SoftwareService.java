package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareDTO;
import org.polsl.backend.dto.software.SoftwareListOutputDTO;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.polsl.backend.type.InventoryNumberEnum;
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
  private final InventoryNumberService inventoryNumberService;

  @Autowired
  public SoftwareService(SoftwareRepository softwareRepository,
                         ComputerSetRepository computerSetRepository,
                         ComputerSetSoftwareRepository computerSetSoftwareRepository,
                         InventoryNumberService inventoryNumberService) {
    this.softwareRepository = softwareRepository;
    this.computerSetRepository = computerSetRepository;
    this.computerSetSoftwareRepository = computerSetSoftwareRepository;
    this.inventoryNumberService = inventoryNumberService;
  }

  public PaginatedResult<SoftwareListOutputDTO> getAllSoftware() {
    Iterable<Software> softwares = softwareRepository.findAll();
    List<SoftwareListOutputDTO> softwareListOutputDTO = new ArrayList<>();
    for (Software software : softwares) {
      SoftwareListOutputDTO dto = new SoftwareListOutputDTO();
      dto.setId(software.getId());
      dto.setName(software.getName());
      softwareListOutputDTO.add(dto);
    }

    PaginatedResult<SoftwareListOutputDTO> response = new PaginatedResult<>();
    response.setItems(softwareListOutputDTO);
    response.setTotalElements((long) softwareListOutputDTO.size());
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
    String newInventoryNumber = inventoryNumberService.generateInventoryNumber(InventoryNumberEnum.SOFTWARE, softwareRepository.count());
    software.setInventoryNumber(newInventoryNumber);
    software.setKey(request.getKey());
    software.setAvailableKeys(request.getAvailableKeys());
    software.setDuration(request.getDuration());
    softwareRepository.save(software);

    //New record(s) in history
    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if (computerSetIdsSet != null) {
      if(request.getComputerSetIds().size() > request.getAvailableKeys())
        throw new BadRequestException("Wybrano więcej urządzeń niż wprowadzono licencji. Operacja nieudana.");

      //Check if computer set is not deleted before creating a new record in history; add dates to software (not history) record
      computerSetIdsSet.forEach(computerSetId -> {

        //Software
        software.setAvailableKeys(software.getAvailableKeys() - 1);
        softwareRepository.save(software);

        //Computer_sets_software
        ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  @Transactional
  public void editSoftware(Long id, SoftwareDTO request) throws NotFoundException {
    //Edit name
    Software software = softwareRepository.findByIdAndValidToIsNull(id).orElseThrow(() -> new NotFoundException("oprogramowanie", "id", id));
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
        ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
        //Check if computer set is not deleted before creating a new record in history
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
