package id.co.indivara.jdt12.miniproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.miniproject.controller.CustomerController;

import id.co.indivara.jdt12.miniproject.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import id.co.indivara.jdt12.miniproject.service.CustomerService;
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

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerController customerController;

    @Test
    public void getAllCustomerTesting() throws Exception{
        List<Customer>customersCheck = customerService.getAll();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/")
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(result -> {
                    List<Customer> customers = Mapper.getAllData(result.getResponse().getContentAsString(), Customer.class);
                    Assertions.assertNotNull(customers);
                    Assertions.assertEquals(customersCheck.get(0).getId(), customers.get(0).getId());
                    log.info(result.getResponse().getContentAsString());

                }).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
    //untuk testing id customers
    @Test
    public void getCustomerById() throws Exception {
        Customer customer = customerService.findById(2L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/customer/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    Customer customers = Mapper.getData(result.getResponse().getContentAsString(), Customer.class);
                    Assertions.assertNotNull(customer);
                    Assertions.assertEquals(customers.getId(),customer.getId());
                    log.info(result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void updateCustomer() throws Exception {
        Customer customer = customerService.findById(3L);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/customer/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.toJson(customer))
                )
                .andDo(result -> {
                    Customer customers = Mapper.getData(result.getResponse().getContentAsString(), Customer.class);
                    Assertions.assertNotNull(customers);
                    Assertions.assertEquals(customers.getId(),customer.getId());
                    log.info(result.getResponse().getContentAsString());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void addAndDeleteCustomer() throws Exception {
        Customer customer = Customer.builder()
                .nik("87623127252")
                .name("Alexander")
                .address("Kalimalang Jl.KH Noer Ali")
                .email("younglex21@gmail.com")
                .phoneNumber("081923444214")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Mapper.toJson(customer))
                ).andDo(result -> {
                    Customer customers = Mapper.getData(result.getResponse().getContentAsString(), Customer.class);
                    Assertions.assertNotNull(customers);
                    Assertions.assertEquals(customers.getName(),customer.getName());
                    deleteCustomer( customers.getId());
                    log.info(result.getResponse().getContentAsString());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    public void deleteCustomer(Long id) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/customer/"+id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    ResponseMessage responseMessage = Mapper.getData(result.getResponse().getContentAsString(), ResponseMessage.class);
                    Assertions.assertNotNull(responseMessage);
                    Assertions.assertEquals("Data Berhasil Dihapus",responseMessage.getMessage());
                    log.info(result.getResponse().getContentAsString());
                })
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty());
    }
}

