package id.co.indivara.jdt12.miniproject;


import id.co.indivara.jdt12.miniproject.controller.TransactionController;

import id.co.indivara.jdt12.miniproject.entity.Transaction;
import id.co.indivara.jdt12.miniproject.entity.Transaction;
import id.co.indivara.jdt12.miniproject.service.TransactionService;
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


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionControllerTest {
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
                }).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty());
    }
    @Test
    public void getTransactionByCustomerId() throws Exception {
        Transaction transaction = transactionService.findById(2L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transaction/customer/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    Transaction transactions = Mapper.getData(result.getResponse().getContentAsString(), Transaction.class);
                    Assertions.assertNotNull(transaction);
                    Assertions.assertEquals(transaction.getId(),transaction.getId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void getTransactionByDriverId() throws Exception {
        Transaction transaction = transactionService.findById(3L);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/transaction/driver/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    Transaction transactions = Mapper.getData(result.getResponse().getContentAsString(), Transaction.class);
                    Assertions.assertNotNull(transaction);
                    Assertions.assertEquals(transaction.getId(),transaction.getId());
                })//transaction.getRent().getDriver().getId()
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
}
