package id.co.indivara.jdt12.miniproject.controller;

import id.co.indivara.jdt12.miniproject.entity.Transaction;
import id.co.indivara.jdt12.miniproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends GenericController <Transaction>{
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/rent/{id}")
    public ResponseEntity<Transaction> finish(@PathVariable Long id){
        return new ResponseEntity<>(transactionService.finish(id), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> findAllTransaction() {
        List<Transaction> transactions = transactionService.findAllTransaction();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/driver/{id}")
    public ResponseEntity<List<Transaction>> findAllTransactionByDriverId(@PathVariable Long id) {
        if (id != null) {
        List<Transaction> transactions = transactionService.findAllTransactionByDriverId(id);
        System.out.println(transactions);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        else{
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Transaction>> findAllTransactionByCustomerId(@PathVariable Long id) {
        List<Transaction> transactions = transactionService.findAllTransactionByCustomerId(id);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @PutMapping("/complete/{id}")
    public ResponseEntity<Transaction> completePayment(@PathVariable Long id){
        return new ResponseEntity<>(transactionService.completePayment(id), HttpStatus.OK);
    }
}
