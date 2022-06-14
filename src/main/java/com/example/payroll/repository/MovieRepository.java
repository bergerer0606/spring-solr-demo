package com.example.payroll.repository;

import com.example.payroll.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface MovieRepository extends SolrCrudRepository<Movie, String> {
    Page<Movie> findAllByTitleIgnoreCase(String title, Pageable pageable);
}
