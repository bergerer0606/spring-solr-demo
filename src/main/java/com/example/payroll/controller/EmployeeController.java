package com.example.payroll.controller;

import com.example.payroll.entity.Employee;
import com.example.payroll.ex.EmployeeNotFoundException;
import com.example.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repo){
        this.repository = repo;
    }

    @GetMapping("/employees")
    List<Employee> all(){
        List<Employee> employees = new ArrayList<>();

        repository.findAll().forEach(employees::add);

        return employees;
    }

    @PostMapping("/employees")
    Employee add(@RequestBody Employee employee){
        return repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable String id){
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }


    @PutMapping("/employees/{id}")
    Employee replace(@RequestBody Employee newEmployee, @PathVariable String id){
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }


    @DeleteMapping("/employees/{id}")
    Employee delete(@PathVariable String id){
        return repository.findById(id).map(employee -> {
            repository.delete(employee);
            return employee;
        }).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @GetMapping("/employees/find")
    List<Employee> find(@RequestParam Optional<String> firstName, @RequestParam Optional<String> lastName){
        if(firstName.isPresent() && lastName.isPresent())
            return repository.findEmployeesByFirstNameAndLastName(firstName.get(), lastName.get());

        if(firstName.isPresent())
            return repository.findEmployeesByFirstName(firstName.get());

        if(lastName.isPresent())
            return repository.findEmployeesByLastName(lastName.get());

        throw new EmployeeNotFoundException("");
    }

}
