package id.co.indivara.jdt12.miniproject;

import id.co.indivara.jdt12.miniproject.controller.VehicleController;
import id.co.indivara.jdt12.miniproject.entity.Vehicle;
import id.co.indivara.jdt12.miniproject.service.VehicleService;
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
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VehicleController vehicleController;
    @Autowired
    private VehicleService vehicleService;

    @Test
    public void getAllVehicleTesting() throws Exception{
        List<Vehicle>vehicleCheck = vehicleService.getAll();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vehicle/")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(result -> {
            List<Vehicle> vehicles = Mapper.getAllData(result.getResponse().getContentAsString(), Vehicle.class);
            Assertions.assertNotNull(vehicles);
            Assertions.assertEquals(vehicleCheck.get(0).getId(), vehicles.get(0).getId());
        }).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
    @Test
    public void getVehicleById() throws Exception {
        Vehicle vehicle = vehicleService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vehicle/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    Vehicle vehicles = Mapper.getData(result.getResponse().getContentAsString(), Vehicle.class);
                    Assertions.assertNotNull(vehicles);
                    Assertions.assertEquals(vehicle.getId(),vehicles.getId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void updateVehicle() throws Exception {
        Vehicle vehicle = vehicleService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/vehicle/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Mapper.toJson(vehicle))
        )
                .andDo(result -> {
                    Vehicle vehicles = Mapper.getData(result.getResponse().getContentAsString(), Vehicle.class);
                    Assertions.assertNotNull(vehicles);
                    Assertions.assertEquals(vehicles.getId(),vehicle.getId());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void addAndDeleteVehicle() throws Exception {
        Vehicle vehicle = Vehicle.builder()
                .name("lamborghini")
                .baggage(1)
                .level(1)
                .plate("B 1004 JT")
                .year(2020)
                .seat(2)
                .isAvailable(true)
                .costPerHour(BigDecimal.valueOf(100000.0))
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/vehicle")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Mapper.toJson(vehicle))
        ).andDo(result -> {
                    Vehicle vehicles = Mapper.getData(result.getResponse().getContentAsString(), Vehicle.class);
                    Assertions.assertNotNull(vehicles);
                    Assertions.assertEquals(vehicles.getId(),vehicle.getId());
                    deleteVehicle(vehicles.getId());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    public void deleteVehicle(Long id) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/vehicle/"+id)
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

