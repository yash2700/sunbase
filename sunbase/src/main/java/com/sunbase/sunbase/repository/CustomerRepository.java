package com.sunbase.sunbase.repository;

import com.sunbase.sunbase.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> getAllBy(Pageable pageable);
    @Query("SELECT t FROM Customer t WHERE " +
            "CASE " +
            "WHEN :columnName = 'first_name' THEN t.first_name " +
            "WHEN :columnName = 'city' THEN t.city " +
            "WHEN :columnName = 'email' THEN t.email " +
            "WHEN :columnName = 'phone' THEN t.phone " +
            "END = :columnValue")
    Page<Customer> findCustomerByColumnName(@Param("columnName") String columnName, @Param("columnValue") String columnValue, Pageable pageable);
}
