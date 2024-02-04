package com.sunbase.sunbase.dtos;

import com.sunbase.sunbase.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String first_name;
    private String last_name;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;
    private String uuid;

    public static Customer toEntity(CustomerDto dto){
        return Customer.builder()
                .first_name(dto.getFirst_name())
                .last_name(dto.getLast_name())
                .address(dto.getAddress())
                .city(dto.getCity())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .street(dto.getStreet())
                .state(dto.getState())
                .build();
    }

    public static CustomerDto toDto(Customer customer){
        return CustomerDto.builder()
                .first_name(customer.getFirst_name())
                .last_name(customer.getLast_name())
                .address(customer.getAddress())
                .city(customer.getCity())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .street(customer.getStreet())
                .state(customer.getState())
                .uuid(customer.getUuid())
                .build();
    }
}
