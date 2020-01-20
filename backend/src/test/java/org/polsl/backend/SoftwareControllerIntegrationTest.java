package org.polsl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
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
    @Sql(scripts = "/scripts/create-test-software.sql"),
    @Sql(scripts = "/scripts/create-test-computer_sets.sql"),
    @Sql(scripts = "/scripts/create-test-computer_sets_software.sql")
})
public class SoftwareControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  //region GET
  @Test
  public void givenCorrectRequest_whenGettingSoftwareList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/software"))
    .andExpect(status().is(200))
    .andExpect(jsonPath("$.totalElements").value(3))
    .andExpect(jsonPath("$.items", hasSize(3)))
    .andExpect(jsonPath("$.items[0].id").value(1))
    .andExpect(jsonPath("$.items[0].availableKeys").value(5))
    .andExpect(jsonPath("$.items[0].duration").value(1607106864))
    .andExpect(jsonPath("$.items[0].inventoryNumber").value("S1/2019"))
    .andExpect(jsonPath("$.items[0].key").value("T847-54GF-7845-FSF5"))
    .andExpect(jsonPath("$.items[0].name").value("Photoshop"))
    .andExpect(jsonPath("$.items[0].validTo").doesNotExist())
    .andExpect(jsonPath("$.items[0].computerSetInventoryNumbers", IsCollectionWithSize.hasSize(2)))
    .andExpect(jsonPath("$.items[0].computerSetInventoryNumbers[0]").value("C2/2019"))
    .andExpect(jsonPath("$.items[0].computerSetInventoryNumbers[1]").value("C1/2019"))
    .andExpect(jsonPath("$.items[1].id").value(2))
    .andExpect(jsonPath("$.items[1].availableKeys").value(3))
    .andExpect(jsonPath("$.items[1].duration").value(1575480864))
    .andExpect(jsonPath("$.items[1].inventoryNumber").value("S2/2019"))
    .andExpect(jsonPath("$.items[1].key").value("874G-54D7-JHKI-LLKI"))
    .andExpect(jsonPath("$.items[1].name").value("Visual Studio"))
    .andExpect(jsonPath("$.items[1].validTo").doesNotExist())
    .andExpect(jsonPath("$.items[1].computerSetInventoryNumbers", IsCollectionWithSize.hasSize(1)))
    .andExpect(jsonPath("$.items[1].computerSetInventoryNumbers[0]").value("C1/2019"))
    .andExpect(jsonPath("$.items[2].id").value(3))
    .andExpect(jsonPath("$.items[2].availableKeys").value(1))
    .andExpect(jsonPath("$.items[2].duration").value(1767116064))
    .andExpect(jsonPath("$.items[2].inventoryNumber").value("S3/2019"))
    .andExpect(jsonPath("$.items[2].key").value("47FD-YIJD-MKN7-PDU5"))
    .andExpect(jsonPath("$.items[2].name").value("Postman"))
    .andExpect(jsonPath("$.items[2].validTo").doesNotExist())
    .andExpect(jsonPath("$.items[2].computerSetInventoryNumbers", IsCollectionWithSize.hasSize(0)));
  }

  @Test
  public void givenCorrectRequest_whenGettingSoftwareHistory_thenReturnStatus200AndData() throws Exception{
    mvc.perform(get("/api/software/1/computer-sets-history"))
    .andExpect(status().is(200))
    .andExpect(jsonPath("$.totalElements").value(3))
    .andExpect(jsonPath("$.items", hasSize(3)))
    .andExpect(jsonPath("$.items[0].inventoryNumber").value("C1/2019"))
    .andExpect(jsonPath("$.items[0].name").value("HP ProBook"))
    .andExpect(jsonPath("$.items[0].validFrom").value("2017-07-23 00:00"))
    .andExpect(jsonPath("$.items[0].validTo").doesNotExist())
    .andExpect(jsonPath("$.items[1].inventoryNumber").value("C2/2019"))
    .andExpect(jsonPath("$.items[1].name").value("ACER Laptop"))
    .andExpect(jsonPath("$.items[1].validFrom").value("2018-09-28 00:00"))
    .andExpect(jsonPath("$.items[1].validTo").doesNotExist())
    .andExpect(jsonPath("$.items[2].inventoryNumber").value("C3/2019"))
    .andExpect(jsonPath("$.items[2].name").value("Lenovo V310"))
    .andExpect(jsonPath("$.items[2].validFrom").value("2018-09-28 00:00"))
    .andExpect(jsonPath("$.items[2].validTo").value("2019-11-04 14:27"));
}

  @Test
  public void givenCorrectRequestWithoutComputerSetId_whenGettingOneSoftware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/software/3"))
    .andExpect(status().is(200))
    .andExpect(jsonPath("$.computerSetIds").isArray())
    .andExpect(jsonPath("$.computerSetIds", hasSize(0)))
    .andExpect(jsonPath("$.name").value("Postman"))
    .andExpect(jsonPath("$.duration").value(1767116064))
    .andExpect(jsonPath("$.inventoryNumber").value("S3/2019"))
    .andExpect(jsonPath("$.key").value("47FD-YIJD-MKN7-PDU5"))
    .andExpect(jsonPath("$.validTo").doesNotExist())
    .andExpect(jsonPath("$.availableKeys").value(1));
  }

  @Test
  public void givenCorrectRequestWithComputerSetId_whenGettingOneSoftware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/software/1"))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.name").value("Photoshop"))
      .andExpect(jsonPath("$.computerSetIds").isArray())
      .andExpect(jsonPath("$.computerSetIds", hasSize(2)))
      .andExpect(jsonPath("$.computerSetIds", hasItem(1)))
      .andExpect(jsonPath("$.computerSetIds", hasItem(2)))
      .andExpect(jsonPath("$.availableKeys").value(5))
      .andExpect(jsonPath("$.duration").value(1607106864))
      .andExpect(jsonPath("$.inventoryNumber").value("S1/2019"))
      .andExpect(jsonPath("$.key").value("T847-54GF-7845-FSF5"))
      .andExpect(jsonPath("$.validTo").doesNotExist());
  }

  @Test
  public void givenInvalidId_whenGettingOneSoftware_thenReturnStatus404() throws Exception {
    mvc.perform(get("/api/software/0"))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '0'"));
  }

  @Test
  public void givenInvalidParameter_whenGettingOneSoftware_thenReturnStatus400() throws Exception {
    mvc.perform(get("/api/software/test"))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Podana wartość nie jest liczbą"));
  }

  @Test
  public void givenInvalidId_whenGettingSoftwareHistory_thenReturnStatus404() throws Exception{
    mvc.perform(get("/api/software/0/computer-sets-history"))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '0'"));
  }

  @Test
  public void givenInvalidParameter_whenGettingSoftwareHistory_thenReturnStatus400() throws Exception{
    mvc.perform(get("/api/software/test/computer-sets-history"))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Podana wartość nie jest liczbą"));
  }
  //endregion

  //region POST
  @Test
  public void givenEmptyRequest_whenAddingSoftware_thenReturnStatus400() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    mvc.perform(post("/api/software")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.fieldErrors", hasSize(4)))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /key/)].message").value("must not be empty"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /availableKeys/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /duration/)].message").value("must not be null"));

  }

  @Test
  public void givenNotExistingComputerSetId_whenAddingSoftware_thenReturnStatus404() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 0);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)1575480864);
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(post("/api/software")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje zestaw komputerowy o id: '0'"));
  }

  @Test
  public void givenDeletedComputerSetId_whenAddingSoftware_thenReturnStatus404() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 3);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)1575480864);
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(post("/api/software/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje zestaw komputerowy o id: '3'"));
  }

  @Test
  public void givenInactiveLicense_whenAddingSoftware_thenReturnStatus400() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 3);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long) 0);
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(post("/api/software/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Wprowadzono nieaktywną licencję."));
  }

  @Test
  public void givenNoAvailableLicenseKeys_whenAddingSoftware_thenReturnStatus400() throws Exception{
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 3);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long) 1607106864);
    request.setAvailableKeys((long) 0);
    request.setComputerSetIds(ids);
    mvc.perform(post("/api/software/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Należy wprowadzić co najmniej jeden dostępny do użycia klucz produktu."));
  }

  @Test
  public void givenMoreComputerSetsThanAvailableKeys_whenAddingSoftware_thenReturnStatus400() throws Exception{
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 2);
    ids.add((long) 1);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long) 1607106864);
    request.setAvailableKeys((long) 1);
    request.setComputerSetIds(ids);
    mvc.perform(post("/api/software/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Wybrano więcej urządzeń niż wprowadzono licencji. Operacja nieudana."));
  }

  @Test
  public void givenCorrectRequestWithoutComputerSetIds_whenAddingSoftware_thenReturnStatus200AndData() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    request.setName("Notepad ++");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    mvc.perform(post("/api/software")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Utworzono oprogramowanie."));
  }

  @Test
  public void givenCorrectRequestWithComputerSetIds_whenAddingSoftware_thenReturnStatus200AndData() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 1);
    ids.add((long) 2);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(post("/api/software/")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Utworzono oprogramowanie."));
  }
  //endregion

  //region PUT
  @Test
  public void givenEmptyRequest_whenEditingSoftware_thenReturnStatus400() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.fieldErrors", hasSize(4)))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /key/)].message").value("must not be empty"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /availableKeys/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /duration/)].message").value("must not be null"));
  }

  @Test
  public void givenInvalidId_whenEditingSoftware_thenReturnStatus404() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    request.setName("Photoshop");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    mvc.perform(put("/api/software/0")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '0'"));
  }

  @Test
  public void givenInvalidParameter_whenEditingOneSoftware_thenReturnStatus400() throws Exception {
    mvc.perform(put("/api/software/test"))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Podana wartość nie jest liczbą"));
  }

  @Test
  public void givenNoId_whenEditingSoftware_thenReturnStatus405() throws Exception {
    mvc.perform(put("/api/software")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(405));
  }

  @Test
  public void givenNotExistingComputerSetId_whenEditingSoftware_thenReturnStatus404() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 0);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje zestaw komputerowy o id: '0'"));
  }

  @Test
  public void givenDeletedComputerSetId_whenEditingSoftware_thenReturnStatus404() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 3);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje zestaw komputerowy o id: '3'"));
  }

  @Test
  public void givenDeletedSoftwareId_whenEditingSoftware_thenReturnStatus404() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    mvc.perform(put("/api/software/4")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '4'"));
  }

  @Test
  public void givenInactiveLicense_whenEditingSoftware_thenReturnStatus400() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 3);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long) 0);
    request.setAvailableKeys((long) 5);
    request.setComputerSetIds(ids);
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Wprowadzono nieaktywną licencję."));
  }

  @Test
  public void givenNoAvailableLicenseKeys_whenEditingSoftware_thenReturnStatus400() throws Exception{
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 3);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long) 1607106864);
    request.setAvailableKeys((long) 0);
    request.setComputerSetIds(ids);
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Należy wprowadzić co najmniej jeden dostępny do użycia klucz produktu."));
  }

  @Test
  public void givenMoreComputerSetsThanAvailableKeys_whenEditingSoftware_thenReturnStatus400() throws Exception{
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 2);
    ids.add((long) 4);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long) 1607106864);
    request.setAvailableKeys((long) 1);
    request.setComputerSetIds(ids);
    mvc.perform(put("/api/software/2")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Wybrano więcej urządzeń niż wprowadzono licencji. Operacja nieudana."));
  }

  @Test
  public void givenCorrectRequestWithoutComputerSetIds_whenEditingSoftware_thenReturnStatus200AndData() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    request.setName("Photoshop");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Zaktualizowano parametry oprogramowania."));
  }

  @Test
  public void givenCorrectRequestWithComputerSetIds_whenEditingSoftware_thenReturnStatus200AndData() throws Exception {
    SoftwareDTO request = new SoftwareDTO();
    Set<Long> ids = new HashSet<>();
    ids.add((long) 1);
    ids.add((long) 2);
    request.setComputerSetIds(ids);
    request.setName("Mathematica");
    request.setKey("KDHI-KDIG-OI48-PDIT");
    request.setDuration((long)(1767116064));
    request.setAvailableKeys((long) 5);
    mvc.perform(put("/api/software/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Zaktualizowano parametry oprogramowania."));
  }
  //endregion

  //region DELETE
  @Test
  public void givenNotExistingSoftwareId_whenDeletingSoftware_thenReturnStatus404() throws Exception {
    mvc.perform(delete("/api/software/0"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '0'"));
  }

  @Test
  public void givenDeletedSoftwareId_whenDeletingSoftware_thenReturnStatus404() throws Exception {
    mvc.perform(delete("/api/software/4"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '4'"));
  }

  @Test
  public void givenNoId_whenDeletingSoftware_thenReturnStatus405() throws Exception {
    mvc.perform(delete("/api/software"))
        .andExpect(status().is(405));
  }

  @Test
  public void givenCorrectRequest_whenDeletingSoftware_thenReturnStatus200AndData() throws Exception {
    mvc.perform(delete("/api/software/1"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Usunięto oprogramowanie."));
  }
  //endregion
}
