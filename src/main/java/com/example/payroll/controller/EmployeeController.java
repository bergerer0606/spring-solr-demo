package com.example.payroll.controller;

import com.example.payroll.entity.Employee;
import com.example.payroll.ex.EmployeeNotFoundException;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @RequestMapping(value="/employees", method = RequestMethod.GET)
    Page<Employee> all(
            @RequestParam(value="page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value="count", required = false, defaultValue = "10") Integer count
    ){
        return service.all(page, count);
    }

    @RequestMapping(value="/employees", method = RequestMethod.POST)
    Employee add(
            @RequestBody Employee employee
    ){
        return service.add(employee);
    }

    @RequestMapping(value="/employees/{id}", method=RequestMethod.GET)
    Employee one(@PathVariable String id){
        return service.one(id);
    }


    @RequestMapping(value="/employees/{id}", method=RequestMethod.PUT)
    Employee replace(@RequestBody Employee newEmployee, @PathVariable String id){
        return service.replace(newEmployee, id);
    }


    @RequestMapping(value="/employees/{id}", method=RequestMethod.DELETE)
    Employee delete(@PathVariable String id){
        return service.delete(id);
    }
}
