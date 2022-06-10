package com.example.payroll.ex;

public class EmployeeNotFoundException extends RuntimeException{

    public final Long id;

    public EmployeeNotFoundException(Long id){
        super("Cannot find employee with id " + id);

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
