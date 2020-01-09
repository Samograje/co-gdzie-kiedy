package org.polsl.backend.service;

import org.polsl.backend.dto.hardwaredictionary.HardwareDictionaryOutputDTO;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.repository.HardwareDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareDictionaryService {
  private HardwareDictionaryRepository hardwareDictionaryRepository;

  @Autowired
  public HardwareDictionaryService(HardwareDictionaryRepository hardwareDictionaryRepository) {
    this.hardwareDictionaryRepository = hardwareDictionaryRepository;
  }

  public List<HardwareDictionaryOutputDTO> getHardwareDictionaryList() {
    Iterable<HardwareDictionary> hardwareDictionaries = hardwareDictionaryRepository.findAll();
    List<HardwareDictionaryOutputDTO> dtos = new ArrayList<>();
    for(HardwareDictionary hardwareDictionary : hardwareDictionaries){
      HardwareDictionaryOutputDTO dto = new HardwareDictionaryOutputDTO();
      dto.setId(hardwareDictionary.getId());
      dto.setValue(hardwareDictionary.getValue());
      dtos.add(dto);
    }
    return dtos;
  }
}
