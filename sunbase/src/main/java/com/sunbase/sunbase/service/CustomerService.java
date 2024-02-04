package com.sunbase.sunbase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.sunbase.dtos.CustomerDto;
import com.sunbase.sunbase.dtos.SearchCustomer;
import com.sunbase.sunbase.dtos.SearchResponse;
import com.sunbase.sunbase.entity.Login;

import java.util.List;
import java.util.Locale;

public interface CustomerService {
    CustomerDto addCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(String uuid);

    CustomerDto deleteCustomerById(String uuid);
    CustomerDto updateCustomer(CustomerDto customerDto);
    SearchResponse viewCustomers(SearchCustomer searchCustomer);
    List<CustomerDto> syncCustomers(Login login) throws JsonProcessingException;
    String login(Login login);
}
