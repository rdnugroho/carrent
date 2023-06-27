package id.co.indivara.jdt12.miniproject.controller;

import id.co.indivara.jdt12.miniproject.entity.Customer;
import id.co.indivara.jdt12.miniproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends GenericController<Customer>{

    @Autowired
    private CustomerService customerService;
}
