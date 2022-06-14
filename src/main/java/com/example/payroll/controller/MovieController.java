package com.example.payroll.controller;

import com.example.payroll.entity.Movie;
import com.example.payroll.ex.MovieCountInvalidException;
import com.example.payroll.ex.MoviePageInvalidException;
import com.example.payroll.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MovieController {
    private final MovieService service;

    @RequestMapping(value="/movies", method = RequestMethod.GET)
    @ResponseBody
    Page<Movie> all(
            @RequestParam(name="count", required=false, defaultValue = "10") Integer count,
            @RequestParam(name="page", required=false, defaultValue="0") Integer pageNum
    ){
        validatePage(pageNum);
        validateCount(count);

        return service.all(pageNum, count);
    }

    @RequestMapping(value="/movies/search", method=RequestMethod.GET)
    Page<Movie> byTitle(
            @RequestParam(name="title") String title,
            @RequestParam(name="count", required = false, defaultValue = "10") Integer count,
            @RequestParam(name="page", required = false, defaultValue = "0") Integer page
    ){
        validatePage(page);
        validateCount(count);

        return service.byTitle(title, page, count);
    }

    private void validateCount(int count){
        if(count < 0 || count > 100)
            throw new MovieCountInvalidException(count);
    }

    private void validatePage(int page){
        if(page < 0)
            throw new MoviePageInvalidException(page);
    }
}
