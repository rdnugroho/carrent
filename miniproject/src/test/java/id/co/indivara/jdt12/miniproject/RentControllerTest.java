package id.co.indivara.jdt12.miniproject;

import id.co.indivara.jdt12.miniproject.controller.RentController;

import id.co.indivara.jdt12.miniproject.entity.Rent;
import id.co.indivara.jdt12.miniproject.service.RentService;

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


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RentController rentController;
    @Autowired
    private RentService rentService;
    @Test
    public void getAllRentTest() throws Exception{
        List<Rent>rentsCheck = rentService.getAll();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/rent/")
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(result -> {
                    List<Rent> rents = Mapper.getAllData(result.getResponse().getContentAsString(), Rent.class);
                    Assertions.assertNotNull(rents);
                    Assertions.assertEquals(rentsCheck.get(0).getId(), rents.get(0).getId());
                }).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
}