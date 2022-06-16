package com.example.payroll.service;

import com.example.payroll.entity.Employee;
import com.example.payroll.ex.EmployeeNotFoundException;
import com.example.payroll.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Page<Employee> all(){
        return all(0, 10);
    }

    public Page<Employee> all(int page, int size){
        Pageable paging = PageRequest.of(page, size);

        return repository.findAll(paging);
    }

    public Employee add(Employee employee){
        return repository.save(employee);
    }

    public Employee one(String id){
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }


    public Employee replace(Employee newEmployee, String id){
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    public Employee delete(String id){
        return repository.findById(id).map(employee -> {
            repository.delete(employee);
            return employee;
        }).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
}
