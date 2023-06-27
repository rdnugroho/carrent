package id.co.indivara.jdt12.miniproject;


import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.miniproject.controller.TransactionController;
import id.co.indivara.jdt12.miniproject.entity.Transaction;
import id.co.indivara.jdt12.miniproject.service.TransactionService;
import id.co.indivara.jdt12.miniproject.utilize.mapper.Mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
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
public class TransactionControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TransactionService transactionService;
    @Test
    public void getAllTransactionTest() throws Exception{
        List<Transaction>transactionsCheck = transactionService.getAll();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transaction/all")
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(result -> {
                    List<Transaction> transactions = Mapper.getAllData(result.getResponse().getContentAsString(), Transaction.class);
                    Assertions.assertNotNull(transactions);
                    Assertions.assertEquals(transactionsCheck.get(0).getId(), transactions.get(0).getId());
                    log.info(result.getResponse().getContentAsString());
                }).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
    @Test
    public void getTransactionByCustomerId() throws Exception {
        Transaction transactionCheck = transactionService.findById(2L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transaction/customer/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    List<Transaction> transaction = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference <List<Transaction>>(){});
                    Assertions.assertNotNull(transaction);
//                    Assertions.assertEquals(transactionCheck.getId(),transaction.getId());
                    log.info(result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void getTransactionByDriverId() throws Exception {
        Transaction transactionCheck = transactionService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transaction/driver/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    List<Transaction> transaction = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference <List<Transaction>>(){});
                    Assertions.assertNotNull(transaction);
                    log.info(result.getResponse().getContentAsString());
//                  Assertions.assertEquals(transactionCheck.getId(),transaction.getId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
}
