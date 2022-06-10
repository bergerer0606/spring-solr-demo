package com.example.payroll.repository;

import com.example.payroll.entity.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderRepository extends SolrCrudRepository<Order, String> {
    List<Order> findByDescriptionContaining(String description);
}
