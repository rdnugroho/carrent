package id.co.indivara.jdt12.miniproject;


import id.co.indivara.jdt12.miniproject.controller.DriverController;
import id.co.indivara.jdt12.miniproject.entity.Driver;

import id.co.indivara.jdt12.miniproject.service.DriverService;
import id.co.indivara.jdt12.miniproject.utilize.mapper.Mapper;
import id.co.indivara.jdt12.miniproject.utilize.response.ResponseMessage;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DriverController driverController;
    @Autowired
    private DriverService driverService;
    @Test
    public void getAllDriverTesting() throws Exception{
        List<Driver>driversCheck = driverService.getAll();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/driver/")
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(result -> {
                    List<Driver> drivers = Mapper.getAllData(result.getResponse().getContentAsString(), Driver.class);
                    Assertions.assertNotNull(drivers);
                    Assertions.assertEquals(driversCheck.get(0).getName(), drivers.get(0).getName());
                }).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
    @Test
    public void getDriverById() throws Exception {
        Driver driver = driverService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/driver/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    Driver drivers = Mapper.getData(result.getResponse().getContentAsString(), Driver.class);
                    Assertions.assertNotNull(driver);
                    Assertions.assertEquals(driver.getName(),driver.getName());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void updateDriver() throws Exception {
        Driver driver = driverService.findById(3L);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/driver/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.toJson(driver))
                )
                .andDo(result -> {
                    Driver drivers = Mapper.getData(result.getResponse().getContentAsString(), Driver.class);
                    Assertions.assertNotNull(drivers);
                    Assertions.assertEquals(drivers.getName(),drivers.getName());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void addAndDeleteDriver() throws Exception {
        Driver driver = Driver.builder()
                .nik("87623127252")
                .name("Alexander")
                .address("Kalimalang Jl.KH Noer Ali")
                .email("younglex21@gmail.com")
                .phoneNumber("081923444214")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/driver")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.toJson(driver))
                ).andDo(result -> {
                    Driver drivers = Mapper.getData(result.getResponse().getContentAsString(), Driver.class);
                    Assertions.assertNotNull(drivers);
                    Assertions.assertEquals(drivers.getName(),drivers.getName());
                    deleteDriver( drivers.getId());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    public void deleteDriver(Long id) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/driver/"+id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    ResponseMessage responseMessage = Mapper.getData(result.getResponse().getContentAsString(), ResponseMessage.class);
                    Assertions.assertNotNull(responseMessage);
                    Assertions.assertEquals("Data Berhasil Dihapus",responseMessage.getMessage());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty());
    }

}
