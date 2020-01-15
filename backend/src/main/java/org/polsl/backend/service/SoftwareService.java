package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.history.HistoryDTO;
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
import org.springframework.data.jpa.domain.Specification;
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

  public PaginatedResult<SoftwareListOutputDTO> getAllSoftware(Specification<Software> softwareSpecification) {
    List<SoftwareListOutputDTO> softwareListOutputDTO = new ArrayList<>();
    Iterable<Software> softwareList;
    Specification<Software> softwareSpecificationWithValidTo = (
            (Specification<Software>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.isNull(root.get("validTo"))
    ).and(softwareSpecification);
    softwareList = softwareRepository.findAll(softwareSpecificationWithValidTo);
    for (Software software : softwareList) {
      SoftwareListOutputDTO dto = new SoftwareListOutputDTO();
      dto.setId(software.getId());
      dto.setName(software.getName());
      dto.setInventoryNumber(software.getInventoryNumber());
      dto.setAvailableKeys(software.getAvailableKeys());
      dto.setKey(software.getKey());
      dto.setDuration(software.getDuration());
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
    dto.setName(software.getName());
    dto.setInventoryNumber(software.getInventoryNumber());
    dto.setAvailableKeys(software.getAvailableKeys());
    dto.setKey(software.getKey());
    dto.setValidTo(software.getValidTo());
    dto.setDuration(software.getDuration());
    return dto;
  }

  public PaginatedResult<HistoryDTO> getSoftwareComputerSetsHistory(Long id) {
    softwareRepository.findByIdAndValidToIsNull(id).orElseThrow(() -> new NotFoundException("oprogramowanie", "id", id));

    List<ComputerSetSoftware> computerSetSoftwareList = computerSetSoftwareRepository.findAllBySoftwareId(id);
    List<HistoryDTO> dtos = new ArrayList<>();

    computerSetSoftwareList.forEach(computerSetSoftware -> {
      HistoryDTO dto = new HistoryDTO();
      dto.setName(computerSetSoftware.getComputerSet().getName());
      dto.setInventoryNumber(computerSetSoftware.getComputerSet().getInventoryNumber());
      dto.setValidFrom(computerSetSoftware.getValidFrom());
      dto.setValidTo(computerSetSoftware.getValidTo());
      dtos.add(dto);
    });

    PaginatedResult<HistoryDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }
  @Transactional
  public void createSoftware(SoftwareDTO request) {
    inputDataValidation(request);

    Software software = new Software();
    String newInventoryNumber = InventoryNumberGenerator.generateInventoryNumber(InventoryNumberEnum.SOFTWARE, softwareRepository.count());
    software.setName(request.getName());
    software.setInventoryNumber(newInventoryNumber);
    software.setKey(request.getKey());
    software.setAvailableKeys(request.getAvailableKeys());
    software.setDuration(request.getDuration());
    softwareRepository.save(software);

    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if (computerSetIdsSet != null) {
      newHistoryRecord(request, software, computerSetIdsSet);
    }

  }

  @Transactional
  public void editSoftware(Long id, SoftwareDTO request) throws NotFoundException {
    //Edit name
    inputDataValidation(request);

    Software software = softwareRepository.findByIdAndValidToIsNull(id).orElseThrow(() -> new NotFoundException("oprogramowanie", "id", id));
    software.setName(request.getName());
    software.setDuration(request.getDuration());
    software.setAvailableKeys(request.getAvailableKeys());
    software.setKey(request.getKey());
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
      newHistoryRecord(request, software, computerSetIdsSet);
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

  private void newHistoryRecord(SoftwareDTO request, Software software, Set<Long> computerSetIdsSet) {
    if(request.getComputerSetIds().size() > request.getAvailableKeys())
      throw new BadRequestException("Wybrano więcej urządzeń niż wprowadzono licencji. Operacja nieudana.");
    //New record(s) in history
    computerSetIdsSet.forEach(computerSetId -> {
      software.setAvailableKeys(software.getAvailableKeys() - 1);
      softwareRepository.save(software);
      ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(computerSetId)
              .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
      //Check if computer set is not deleted before creating a new record in history
      ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
      computerSetSoftwareRepository.save(computerSetSoftware);
    });
  }

  private void inputDataValidation(SoftwareDTO request){
    if(request.getInventoryNumber() != null)
      throw new BadRequestException("Zakaz ręcznego wprowadzania numeru inwentarzowego.");

    if(request.getAvailableKeys() <= 0)
      throw new BadRequestException("Należy wprowadzić co najmniej jeden dostępny do użycia klucz produktu.");

    if(request.getDuration() <= 0)
      throw new BadRequestException("Wprowadzono nieaktywną licencję.");
  }
}
