package com.example.payroll.service;

import com.example.payroll.entity.Movie;
import com.example.payroll.ex.MovieCountInvalidException;
import com.example.payroll.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public Page<Movie> all(Integer pageNum, Integer count){
        Pageable paging = PageRequest.of(pageNum, count);

        return repository.findAll(paging);
    }

    public Page<Movie> byTitle(String title, Integer page, Integer count){
        Pageable paging = PageRequest.of(page, count);

        return repository.findAllByTitleIgnoreCase(title, paging);
    }
}
