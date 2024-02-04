package com.sunbase.sunbase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunbase.sunbase.Exception.CustomerNotFoundException;
import com.sunbase.sunbase.Helpers.HelperFunctions;
import com.sunbase.sunbase.Helpers.HttpRequestUtils;
import com.sunbase.sunbase.dtos.CustomerDto;
import com.sunbase.sunbase.dtos.SearchCustomer;
import com.sunbase.sunbase.dtos.SearchResponse;
import com.sunbase.sunbase.entity.Customer;
import com.sunbase.sunbase.entity.Login;
import com.sunbase.sunbase.repository.CustomerRepository;
import com.sunbase.sunbase.repository.LoginRepository;
import com.sunbase.sunbase.utils.CustomerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    Environment environment;
    @Autowired
    CustomerRepository repository;
    @Autowired
    HttpRequestUtils httpRequestUtils;
    @Autowired
    LoginRepository loginRepository;

    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = CustomerDto.toEntity(customerDto);
        customer.setUuid(HelperFunctions.generateId());
        repository.save(customer);
        logger.info("Customer saved with id: " + customer.getUuid());
        customerDto.setUuid(customer.getUuid());
        return customerDto;
    }

    @Override
    public CustomerDto getCustomerById(String uuid) {
        if (!repository.existsById(uuid)) {
            logger.error("Customer Not found in DB! Please enter a valid Id!");
            throw new CustomerNotFoundException(environment.getProperty(CustomerUtils.Not_Found.toString()));
        } else {
            Customer customer = repository.findById(uuid).get();
            return CustomerDto.toDto(customer);
        }
    }

    @Override
    public CustomerDto deleteCustomerById(String uuid) {
        if (!repository.existsById(uuid)) {
            logger.error("Customer Not found in DB! Please enter a valid Id!");
            throw new CustomerNotFoundException(environment.getProperty(CustomerUtils.Not_Found.toString()));
        } else {
            Customer customer = repository.findById(uuid).get();
            repository.deleteById(uuid);
            logger.info("customer deleted with ID - "+customer.getUuid());
            return CustomerDto.toDto(customer);
        }
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        if (!repository.existsById(customerDto.getUuid())) {
            logger.error("Customer Not found in DB! Please enter a valid Id!");
            throw new CustomerNotFoundException(environment.getProperty(CustomerUtils.Not_Found.toString()));
        } else {
            Customer customer = CustomerDto.toEntity(customerDto);
            customer.setUuid(customerDto.getUuid());
            repository.save(customer);
        }
        return customerDto;
    }

    @Override
    public SearchResponse viewCustomers(SearchCustomer searchCustomer) {
        if(searchCustomer.getColumnName()==null || searchCustomer.getColumnName().isEmpty()){
            int pageNo = searchCustomer.getPageNo();
            int pageSize = searchCustomer.getPageSize();
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Customer> page = repository.findAll(pageable);
            List<CustomerDto>  list= page.get().toList().stream().map(CustomerDto::toDto).collect(Collectors.toList());
            return SearchResponse.builder()
                    .pageNo(page.getNumber())
                    .pageSize(page.getSize())
                    .totalElements(page.getNumberOfElements())
                    .totalPages(page.getTotalPages())
                    .list(list)
                    .isLast(page.isLast())
                    .build();
        }
        int pageNo = searchCustomer.getPageNo();
        int pageSize = searchCustomer.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Customer> page = repository.findCustomerByColumnName(searchCustomer.getColumnName(), searchCustomer.getColumnValue(), pageable);
        List<Customer> customers = page.get().toList();
        List<CustomerDto> customerDtos = customers.stream().map(CustomerDto::toDto).collect(Collectors.toList());
        return SearchResponse.builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .list(customerDtos)
                .isLast(page.isLast())
                .build();
    }

    @Override
    public List<CustomerDto> syncCustomers(Login login) throws JsonProcessingException {
       List<Customer> customers= httpRequestUtils.getList(login);
        repository.saveAll(customers);
        customers.addAll(repository.findAll());
        return customers.stream().map(CustomerDto::toDto).collect(Collectors.toList());
    }

    @Override
    public String login(Login login) {
        if(!loginRepository.existsById(login.getLogin_id())){
            logger.error("Customer Not found in DB! Please enter a valid Id!");
            throw new CustomerNotFoundException(CustomerUtils.Invalid_Credentials.toString());
        }
        else {
            Login login1=loginRepository.findById(login.getLogin_id()).get();
            if(login1.getPassword().equals(login.getPassword())){
                return "success";
            }
            else{
                logger.error("Invalid email/password. Please enter valid credentials!");
                throw new CustomerNotFoundException(CustomerUtils.Invalid_Credentials.toString());

            }
        }
    }

}
