package com.sunbase.sunbase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.sunbase.dtos.CustomerDto;
import com.sunbase.sunbase.dtos.SearchCustomer;
import com.sunbase.sunbase.dtos.SearchResponse;
import com.sunbase.sunbase.entity.Login;
import com.sunbase.sunbase.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService service;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login){
        return new ResponseEntity<>(service.login(login),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(service.addCustomer(customerDto), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") String customerId) {
        return new ResponseEntity<>(service.getCustomerById(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<CustomerDto> deleteCustomerById(@PathVariable("customerId") String customerId) {
        return new ResponseEntity<>(service.deleteCustomerById(customerId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(service.updateCustomer(customerDto), HttpStatus.OK);
    }

    @PostMapping("/viewCustomers")
    public ResponseEntity<SearchResponse> viewCustomer(@RequestBody SearchCustomer searchCustomer) {
        return new ResponseEntity<>(service.viewCustomers(searchCustomer), HttpStatus.OK);
    }
    @PostMapping("/sync")
    public ResponseEntity<List<CustomerDto>> syncCustomers(@RequestBody Login login) throws JsonProcessingException {
        return new ResponseEntity<>(service.syncCustomers(login), HttpStatus.OK);
    }
}
