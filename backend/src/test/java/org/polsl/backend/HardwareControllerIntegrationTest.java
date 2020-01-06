package org.polsl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.polsl.backend.dto.hardware.HardwareDTO;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
    @Sql(scripts = "/scripts/create-test-hardware_dictionary.sql"),
    @Sql(scripts = "/scripts/create-test-hardware.sql"),
    @Sql(scripts = "/scripts/create-test-computer_sets.sql"),
    @Sql(scripts = "/scripts/create-test-computer_sets_hardware.sql"),
    @Sql(scripts = "/scripts/create-test-affiliation.sql"),
    @Sql(scripts = "/scripts/create-test-affiliation_hardware.sql")
})
public class HardwareControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenCorrectRequest_whenGettingSoloHardwareList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware?solo-only=true"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.totalElements").value(2))
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(jsonPath("$.items[0].id").value(2))
        .andExpect(jsonPath("$.items[0].name").value("TP-Link"))
        .andExpect(jsonPath("$.items[0].type").value("Karta sieciowa"))
        .andExpect(jsonPath("$.items[0].inventoryNumber").value("H2/2019"))
        .andExpect(jsonPath("$.items[0].affiliationName").value("Bartłomiej Szlachta - 130"))
        .andExpect(jsonPath("$.items[1].id").value(4))
        .andExpect(jsonPath("$.items[1].name").value("i3-5400"))
        .andExpect(jsonPath("$.items[1].type").value("Procesor"))
        .andExpect(jsonPath("$.items[1].inventoryNumber").value("H4/2019"))
        .andExpect(jsonPath("$.items[1].affiliationName").value("Jan Kowalski"));
  }

  @Test
  public void givenCorrectRequest_whenGettingHardwareList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.totalElements").value(4))
        .andExpect(jsonPath("$.items", hasSize(4)))
        .andExpect(jsonPath("$.items[0].id").value(1))
        .andExpect(jsonPath("$.items[0].name").value("GTX 1040"))
        .andExpect(jsonPath("$.items[0].type").value("Karta graficzna"))
        .andExpect(jsonPath("$.items[0].inventoryNumber").value("H1/2019"))
        .andExpect(jsonPath("$.items[0].computerSetInventoryNumber").value("C1/2019"))
        .andExpect(jsonPath("$.items[0].affiliationName").value("Szymon Jęczyzel - Solaris"))
        .andExpect(jsonPath("$.items[1].id").value(2))
        .andExpect(jsonPath("$.items[1].name").value("TP-Link"))
        .andExpect(jsonPath("$.items[1].type").value("Karta sieciowa"))
        .andExpect(jsonPath("$.items[1].inventoryNumber").value("H2/2019"))
        .andExpect(jsonPath("$.items[1].affiliationName").value("Bartłomiej Szlachta - 130"))
        .andExpect(jsonPath("$.items[2].id").value(3))
        .andExpect(jsonPath("$.items[2].name").value("i5-7070"))
        .andExpect(jsonPath("$.items[2].type").value("Procesor"))
        .andExpect(jsonPath("$.items[2].inventoryNumber").value("H3/2019"))
        .andExpect(jsonPath("$.items[2].computerSetInventoryNumber").value("C1/2019"))
        .andExpect(jsonPath("$.items[2].affiliationName").value("Bartłomiej Szlachta - 130"))
        .andExpect(jsonPath("$.items[3].id").value(4))
        .andExpect(jsonPath("$.items[3].name").value("i3-5400"))
        .andExpect(jsonPath("$.items[3].type").value("Procesor"))
        .andExpect(jsonPath("$.items[3].inventoryNumber").value("H4/2019"))
        .andExpect(jsonPath("$.items[3].affiliationName").value("Jan Kowalski"));
  }

  @Test
  public void givenIncorrectRequest_whenGettingHardwareList_thenReturnStatus400() throws Exception {
    mvc.perform(get("/api/hardware/dsfsdfsd"))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Podana wartość nie jest liczbą"));
  }

  @Test
  public void givenInvalidId_whenGettingOneHardware_thenReturnStatus404() throws Exception {
    mvc.perform(delete("/api/hardware/0"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje sprzęt o id: '0'"));
  }

  @Test
  public void givenInvalidParameter_whenGettingOneHardware_thenReturnStatus400() throws Exception {
    mvc.perform(get("/api/hardware/mvvm"))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Podana wartość nie jest liczbą"));
  }

  @Test
  public void givenCorrectRequestWithComputerSetId_whenGettingOneHardware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware/1"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.name").value("GTX 1040"))
        .andExpect(jsonPath("$.dictionaryId").value(1))
        .andExpect(jsonPath("$.computerSetId").value(1))
        .andExpect(jsonPath("$.inventoryNumber").value("H1/2019"))
        .andExpect(jsonPath("$.affiliationId").value(1));
  }

  @Test
  public void givenCorrectRequestWithoutComputerSetId_whenGettingOneHardware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware/2"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.name").value("TP-Link"))
        .andExpect(jsonPath("$.dictionaryId").value(2))
        .andExpect(jsonPath("$.inventoryNumber").value("H2/2019"))
        .andExpect(jsonPath("$.affiliationId").value(2));
  }

  @Test
  public void givenCorrectRequest_whenGettingAffiliationHistoryForOneHardware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware/1/affiliations-history"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.totalElements").value(3))
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.items[0].inventoryNumber").doesNotExist())
        .andExpect(jsonPath("$.items[0].name").value("Szymon Jęczyzel - Solaris"))
        .andExpect(jsonPath("$.items[0].validFrom").value("2019-07-12 12:00"))
        .andExpect(jsonPath("$.items[0].validTo").value("2019-07-14 12:00"))
        .andExpect(jsonPath("$.items[1].inventoryNumber").doesNotExist())
        .andExpect(jsonPath("$.items[1].name").value("Bartłomiej Szlachta - 130"))
        .andExpect(jsonPath("$.items[1].validFrom").value("2019-07-14 12:00"))
        .andExpect(jsonPath("$.items[1].validTo").value("2019-09-10 12:00"))
        .andExpect(jsonPath("$.items[2].inventoryNumber").doesNotExist())
        .andExpect(jsonPath("$.items[2].name").value("Szymon Jęczyzel - Solaris"))
        .andExpect(jsonPath("$.items[2].validFrom").value("2019-09-10 12:00"))
        .andExpect(jsonPath("$.items[2].validTo").doesNotExist());
  }

  @Test
  public void givenCorrectRequest_whenGettingComputerSetsHistoryForOneHardware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware/3/computer-sets-history"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.totalElements").value(3))
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.items[0].inventoryNumber").value("C1/2019"))
        .andExpect(jsonPath("$.items[0].name").value("HP ProBook"))
        .andExpect(jsonPath("$.items[0].validFrom").value("2017-07-23 12:00"))
        .andExpect(jsonPath("$.items[0].validTo").value("2017-08-01 12:00"))
        .andExpect(jsonPath("$.items[1].inventoryNumber").value("C2/2019"))
        .andExpect(jsonPath("$.items[1].name").value("ACER Laptop"))
        .andExpect(jsonPath("$.items[1].validFrom").value("2017-08-01 12:00"))
        .andExpect(jsonPath("$.items[1].validTo").value("2018-01-01 12:00"))
        .andExpect(jsonPath("$.items[2].inventoryNumber").value("C1/2019"))
        .andExpect(jsonPath("$.items[2].name").value("HP ProBook"))
        .andExpect(jsonPath("$.items[2].validFrom").value("2018-01-01 12:00"))
        .andExpect(jsonPath("$.items[2].validTo").doesNotExist());
  }

  @Test
  public void givenEmptyRequest_whenAddingHardware_thenReturnStatus400() throws Exception {
    HardwareDTO request = new HardwareDTO();
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /dictionaryId/)].message").value("must not be null"))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /affiliationId/)].message").value("must not be null"));
  }

  @Test
  public void givenInvalidAffiliationId_whenAddingHardware_thenReturnStatus404() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("RTX 2000");
    request.setAffiliationId((long) 0);
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje przynależność o id: '0'"));
  }

  @Test
  public void givenInvalidRequestWithInventoryNumber_whenAddingHardware_thenReturnStatus400() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("RTX 2000");
    request.setAffiliationId((long) 0);
    request.setInventoryNumber("000001/2019");
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Zakaz ręcznego wprowadzania numeru inwentarzowego."));
  }

  @Test
  public void givenInvalidComputerSetId_whenAddingHardware_thenReturnStatus404() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("RTX 2000");
    request.setAffiliationId((long) 1);
    request.setComputerSetId((long) 0);
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje zestaw komputerowy o id: '0'"));
  }

  @Test
  public void givenCorrectRequestWithComputerSetId_whenAddingHardware_thenReturnStatus200AndData() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("GTX 7070");
    request.setAffiliationId((long) 1);
    request.setComputerSetId((long) 1);
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Utworzono sprzęt"));
  }

  @Test
  public void givenCorrectRequestWithoutComputerSetId_whenAddingHardware_thenReturnStatus200AndData() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("GTX 7050");
    request.setAffiliationId((long) 2);
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Utworzono sprzęt"));
  }

  @Test
  public void givenEmptyRequest_whenEditingHardware_thenReturnStatus400() throws Exception {
    HardwareDTO request = new HardwareDTO();
    mvc.perform(put("/api/hardware/1")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /dictionaryId/)].message").value("must not be null"))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /affiliationId/)].message").value("must not be null"));
  }

  @Test
  public void givenInvalidId_whenEditingHardware_thenReturnStatus404() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("Gigabyte 1234");
    request.setComputerSetId((long) 1);
    request.setDictionaryId((long) 1);
    request.setAffiliationId((long) 1);
    mvc.perform(put("/api/hardware/0")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje sprzęt o id: '0'"));
  }

  @Test
  public void givenInvalidRequestWithInventoryNumber_whenEditingHardware_thenReturnStatus400() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("Gigabyte 1234");
    request.setComputerSetId((long) 1);
    request.setDictionaryId((long) 1);
    request.setInventoryNumber("000001/2019");
    request.setAffiliationId((long) 1);
    mvc.perform(put("/api/hardware/1")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Zakaz edycji numeru inwentarzowego."));
  }

  @Test
  public void givenNoId_whenEditingHardware_thenReturnStatus405() throws Exception {
    mvc.perform(put("/api/hardware")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(405));
  }

  @Test
  public void givenInvalidAffiliationId_whenEditingHardware_thenReturnStatus404() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("Gigabyte 4321");
    request.setDictionaryId((long) 1);
    request.setAffiliationId((long) 0);
    mvc.perform(put("/api/hardware/1")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje przynależność o id: '0'"));
  }

  @Test
  public void givenInvalidComputerSetId_whenEditingHardware_thenReturnStatus404() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("Gigabyte 4334");
    request.setComputerSetId((long) 0);
    request.setDictionaryId((long) 1);
    request.setAffiliationId((long) 1);
    mvc.perform(put("/api/hardware/1")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje zestaw komputerowy o id: '0'"));
  }

  @Test
  public void givenCorrectRequestWithoutComputerSetId_whenEditingHardware_thenReturnStatus200AndData() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("GTX 1070Ti");
    request.setDictionaryId((long) 1);
    request.setAffiliationId((long) 2);
    mvc.perform(put("/api/hardware/2")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Zaktualizowano parametry sprzętu"));
  }

  @Test
  public void givenCorrectRequestWithComputerSetId_whenEditingHardware_thenReturnStatus200AndData() throws Exception {
    HardwareDTO request = new HardwareDTO();
    request.setName("WiFi Receiver");
    request.setComputerSetId((long) 2);
    request.setDictionaryId((long) 2);
    request.setAffiliationId((long) 4);
    mvc.perform(put("/api/hardware/3")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Zaktualizowano parametry sprzętu"));
  }

  @Test
  public void givenInvalidId_whenDeletingHardware_thenReturnStatus404() throws Exception {
    mvc.perform(delete("/api/hardware/0"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje sprzęt o id: '0'"));
  }

  @Test
  public void givenNoId_whenDeletingHardware_thenReturnStatus405() throws Exception {
    mvc.perform(delete("/api/hardware"))
        .andExpect(status().is(405));
  }

  @Test
  public void givenCorrectRequest_whenDeletingHardware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(delete("/api/hardware/2"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Usunięto sprzęt"));
  }
}
