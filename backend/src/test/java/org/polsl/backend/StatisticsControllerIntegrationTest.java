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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
        @Sql(scripts = "/scripts/create-test-affiliation.sql"),
        @Sql(scripts = "/scripts/create-test-hardware_dictionary.sql"),
        @Sql(scripts = "/scripts/create-test-hardware.sql"),
        @Sql(scripts = "/scripts/create-test-software.sql"),
        @Sql(scripts = "/scripts/create-test-computer_sets.sql")
})

public class StatisticsControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Test
  public void givenCorrectRequest_whenGettingStatistics_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/statistics"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.affiliationsCount").value(3))
            .andExpect(jsonPath("$.computerSetsCount").value(3))
            .andExpect(jsonPath("$.hardwareCount").value(4))
            .andExpect(jsonPath("$.softwareCount").value(3));
  }


}
