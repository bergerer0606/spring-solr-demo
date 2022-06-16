package com.example.payroll.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Objects;

@SolrDocument
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Field
    private String id;
    @Field
    private String firstName;
    @Field
    private String lastName;
    @Field
    private String role;

    public Employee(String name, String role){
        String[] names = name.split(" " );
        firstName = names[0];
        lastName = names[1];
    }

    public Employee(String firstName, String lastName, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setName(String name) {
        String[] names = name.split(" ");
        this.firstName = names[0];
        this.lastName = names[1];
    }
}
