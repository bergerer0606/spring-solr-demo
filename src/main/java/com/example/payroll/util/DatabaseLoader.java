package com.example.payroll.util;

import com.example.payroll.entity.Employee;
import com.example.payroll.entity.Order;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    @Bean
    CommandLineRunner initEmployees(EmployeeRepository repo){
        return args -> {

        };
    }
}
