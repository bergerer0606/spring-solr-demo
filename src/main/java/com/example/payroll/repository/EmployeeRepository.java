package com.example.payroll.repository;

import com.example.payroll.entity.Employee;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface EmployeeRepository extends SolrCrudRepository<Employee, String> {
    List<Employee> findEmployeesByFirstName(String firstName);

    List<Employee> findEmployeesByLastName(String lastName);

    List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName);
}
