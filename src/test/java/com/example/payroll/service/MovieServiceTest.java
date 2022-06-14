package com.example.payroll.service;

import com.example.payroll.entity.Movie;
import com.example.payroll.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository repository;

    @Captor
    ArgumentCaptor<Pageable> pagingCaptor;

    @Test
    void testAllMovies(){
        //given
        Movie movie1 = new Movie("1", "Toy Story", "", "A toy lol",
                "released", new Date(), 100000L,
                1000L, 120.0, "Disney");
        Movie movie2 = new Movie("2", "Toy Story 2", "", "A toy lol",
                "released", new Date(), 111111L,
                1000000L, 160.0, "Disney");
        Movie movie3 = new Movie("3", "Cars", "", "A car lol",
                "released", new Date(), 99999L,
                9999999L, 120.0, "Pixar?");

        List<Movie> movieList = List.of(movie1, movie2, movie3);

        Page<Movie> moviePage = new PageImpl<>(movieList,
                PageRequest.of(0, 10), movieList.size());


        Mockito.when(repository.findAll(pagingCaptor.capture())).thenReturn(moviePage);
        //when
        Page<Movie> returnedPage = service.all(0, 10);
        //then
        List<Movie> difference = returnedPage.stream().filter(movie -> !movieList.contains(movie))
                .collect(Collectors.toList());

        Assertions.assertThat(difference.size()).isEqualTo(0);
    }

    @Test
    void testByTitle(){
        //given
        Movie movie1 = new Movie("1", "Toy Story", "", "A toy lol",
                "released", new Date(), 100000L,
                1000L, 120.0, "Disney");
        Movie movie2 = new Movie("2", "Toy Story 2", "", "A toy lol",
                "released", new Date(), 111111L,
                1000000L, 160.0, "Disney");
        Movie movie3 = new Movie("3", "Cars", "", "A car lol",
                "released", new Date(), 99999L,
                9999999L, 120.0, "Pixar?");
        Movie movie4 = new Movie("4", "Cars 2", "", "Another cars movie",
                "released", new Date(), 9999L,
                99999L, 100.0, "Pixar?");
        Movie movie5 = new Movie("5", "Planes", "", "A planes movie",
                "released", new Date(), 99999L,
                9999999L, 120.0, "Pixar?");

        List<Movie> carsMovieList = List.of(movie3, movie4);

        Page<Movie> moviePage = new PageImpl<>(carsMovieList,
                PageRequest.of(0, 10), carsMovieList.size());

        Mockito.when(repository.findAllByTitleIgnoreCase(any(), pagingCaptor.capture())).thenReturn(moviePage);

        //when
        Page<Movie> returnedMovies = service.byTitle("cars", 0, 10);

        //then
        Assertions.assertThat(
                returnedMovies.stream()
                        .filter(movie -> !carsMovieList.contains(movie))
                        .count()
        ).isEqualTo(0);
    }
}
