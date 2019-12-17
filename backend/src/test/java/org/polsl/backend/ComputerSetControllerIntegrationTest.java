package org.polsl.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
    @Sql(scripts = "/scripts/create-test-computer_sets.sql")
})
public class ComputerSetControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

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
}
