package org.polsl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.polsl.backend.dto.computerset.ComputerSetDTO;
import org.polsl.backend.dto.software.SoftwareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
    @Sql(scripts = "/scripts/create-test-computer_sets.sql"),
    @Sql(scripts = "/scripts/create-test-software.sql"),
    @Sql(scripts = "/scripts/create-test-affiliation.sql"),
    @Sql(scripts = "/scripts/create-test-hardware_dictionary.sql"),
    @Sql(scripts = "/scripts/create-test-hardware.sql"),
    @Sql(scripts = "/scripts/create-test-affiliation_hardware.sql")
})
public class ComputerSetControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  //region GET
  @Test
  public void givenCorrectRequest_whenGettingComputerSetsList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/computer-sets"))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.totalElements").value(3))
      .andExpect(jsonPath("$.items", hasSize(3)))
      .andExpect(jsonPath("$.items[0].id").value(1))
      .andExpect(jsonPath("$.items[0].name").value("HP ProBook"))
      .andExpect(jsonPath("$.items[0].inventoryNumber").value("C1/2019"))
      .andExpect(jsonPath("$.items[1].id").value(2))
      .andExpect(jsonPath("$.items[1].name").value("ACER Laptop"))
      .andExpect(jsonPath("$.items[1].inventoryNumber").value("C2/2019"))
      .andExpect(jsonPath("$.items[2].id").value(4))
      .andExpect(jsonPath("$.items[2].name").value("Lenovo Legion"))
      .andExpect(jsonPath("$.items[2].inventoryNumber").value("C4/2019"));
  }
  //TODO: Kamil: testy dla pobierania pojedyńczego oprogramowania (nie widzę takiej metody w serwisie), poprawny request, ze złym id, czymś innym niż id
  //endregion

  //region POST
  @Test
  public void givenEmptyRequest_whenAddingComputerSet_thenReturnStatus400() throws Exception{
    ComputerSetDTO request = new ComputerSetDTO();
    mvc.perform(post("/api/computer-sets")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.fieldErrors", IsCollectionWithSize.hasSize(4)))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /affiliationId/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /hardwareIds/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /softwareIds/)].message").value("must not be null"));
  }

  @Test
  public void givenCorrectRequest_whenAddingComputerSet_thenReturnStatus200AndData() throws Exception {
    ComputerSetDTO request = new ComputerSetDTO();
    Set<Long> hardwareIds = new HashSet<>();
    Set<Long> softwareIds = new HashSet<>();
    hardwareIds.add((long) 1);
    hardwareIds.add((long) 2);
    softwareIds.add((long) 1);
    softwareIds.add((long) 2);
    request.setName("Lenovo Yoga");
    request.setAffiliationId((long) 1);
    request.setHardwareIds(hardwareIds);
    request.setSoftwareIds(softwareIds);
    mvc.perform(post("/api/computer-sets/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Utworzono Zestaw komputerowy"));
  }

  @Test
  public void givenNotExistingAffiliationId_whenAddingComputerSet_thenReturnStatus404() throws Exception{
    ComputerSetDTO request = new ComputerSetDTO();
    Set<Long> hardwareIds = new HashSet<>();
    Set<Long> softwareIds = new HashSet<>();
    hardwareIds.add((long) 1);
    hardwareIds.add((long) 2);
    softwareIds.add((long) 1);
    softwareIds.add((long) 2);
    request.setName("Lenovo Yoga");
    request.setAffiliationId((long) 0);
    request.setHardwareIds(hardwareIds);
    request.setSoftwareIds(softwareIds);
    mvc.perform(post("/api/computer-sets/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje przynależność o id: '0'"));
  }

  @Test
  public void givenNotExistingHardwareId_whenAddingComputerSet_thenReturnStatus404() throws Exception{
    ComputerSetDTO request = new ComputerSetDTO();
    Set<Long> hardwareIds = new HashSet<>();
    Set<Long> softwareIds = new HashSet<>();
    hardwareIds.add((long) 0);
    softwareIds.add((long) 1);
    softwareIds.add((long) 2);
    request.setName("Lenovo Yoga");
    request.setAffiliationId((long) 1);
    request.setHardwareIds(hardwareIds);
    request.setSoftwareIds(softwareIds);
    mvc.perform(post("/api/computer-sets/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje sprzęt o id: '0'"));
  }

  @Test
  public void givenNotExistingSoftwareId_whenAddingComputerSet_thenReturnStatus404() throws Exception{
    ComputerSetDTO request = new ComputerSetDTO();
    Set<Long> hardwareIds = new HashSet<>();
    Set<Long> softwareIds = new HashSet<>();
    hardwareIds.add((long) 1);
    hardwareIds.add((long) 2);
    softwareIds.add((long) 0);
    request.setName("Lenovo Yoga");
    request.setAffiliationId((long) 1);
    request.setHardwareIds(hardwareIds);
    request.setSoftwareIds(softwareIds);
    mvc.perform(post("/api/computer-sets/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '0'"));
  }

  @Test
  public void givenCorrectRequestWithInventoryNumber_whenAddingComputerSet_thenReturnStatus40() throws Exception{
    ComputerSetDTO request = new ComputerSetDTO();
    Set<Long> hardwareIds = new HashSet<>();
    Set<Long> softwareIds = new HashSet<>();
    hardwareIds.add((long) 1);
    hardwareIds.add((long) 2);
    softwareIds.add((long) 0);
    request.setName("Lenovo Yoga");
    request.setAffiliationId((long) 1);
    request.setHardwareIds(hardwareIds);
    request.setSoftwareIds(softwareIds);
    request.setInventoryNumber("C2/2019");
    mvc.perform(post("/api/computer-sets/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Zakaz ręcznego wprowadzania numeru inwentarzowego."));
  }

  //endregion

}
