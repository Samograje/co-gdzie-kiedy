package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.hardware.HardwareInputDTO;
import org.polsl.backend.dto.hardware.HardwareOutputDTO;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareDictionaryRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Logika biznesowa hardware'u.
 */
@Service
public class HardwareService {
  private final HardwareRepository hardwareRepository;
  private final HardwareDictionaryRepository hardwareDictionaryRepository;
  private final ComputerSetRepository computerSetRepository;
  private final ComputerSetHardwareRepository computerSetHardwareRepository;

  @Autowired
  public HardwareService(HardwareRepository hardwareRepository, HardwareDictionaryRepository hardwareDictionaryRepository, ComputerSetRepository computerSetRepository, ComputerSetHardwareRepository computerSetHardwareRepository) {
    this.hardwareRepository = hardwareRepository;
    this.hardwareDictionaryRepository = hardwareDictionaryRepository;
    this.computerSetRepository = computerSetRepository;
    this.computerSetHardwareRepository = computerSetHardwareRepository;
  }

  public PaginatedResult<HardwareOutputDTO> getAllSoloHardware() {
    Iterable<Hardware> soloHardware = hardwareRepository.findAllByComputerSetHardwareSetIsNull();
    List<HardwareOutputDTO> dtos = new ArrayList<>();
    for (Hardware hardware : soloHardware) {
      HardwareOutputDTO dto = new HardwareOutputDTO();
      dto.setId(hardware.getId());
      dto.setName(hardware.getName());
      dto.setType(hardware.getHardwareDictionary().getValue());
      dtos.add(dto);
    }
    PaginatedResult<HardwareOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public void createAffiliation(HardwareInputDTO request) {
    Hardware hardware = new Hardware();
    hardware.setName(request.getName());
    HardwareDictionary hardwareDictionary = hardwareDictionaryRepository.findById(request.getDictionaryId())
        .orElseThrow(() -> new NotFoundException("słownik urządzeń", "id", request.getDictionaryId() ));
    hardware.setHardwareDictionary(hardwareDictionary);

    if(request.getComputerSetId() != null){
      ComputerSet computerSet = computerSetRepository.findById(request.getComputerSetId())
          .orElseThrow(()-> new NotFoundException("zestawy komputerowe", "id", request.getComputerSetId()));
      ComputerSetHardware computerSetHardware = new ComputerSetHardware();
      computerSetHardware.setComputerSet(computerSet);
      computerSetHardware.setValidFrom(LocalDateTime.now());
      computerSetHardware.setValidTo(null);

      HashSet<ComputerSetHardware> computerSetHardwareSet = new HashSet<>();
      //TODO: tworzyć repo
      //TODO: master fetch i merge na mojej galezi

      computerSetHardwareSet.add(computerSetHardware);
      hardware.setComputerSetHardwareSet(computerSetHardwareSet);
    }
    else{
      hardware.setComputerSetHardwareSet(null);
    }



    //hardware.setAffiliationHardwareSet();

    hardwareRepository.save(hardware);
  }
}
