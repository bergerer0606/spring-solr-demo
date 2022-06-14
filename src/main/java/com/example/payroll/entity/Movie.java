package com.example.payroll.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;
import java.util.Objects;

@SolrDocument
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @Field private String id;

    @Field private String title;
    @Field private String tagline;
    @Field private String overview;
    @Field private String status;
    @Field private Date releaseDate;
    @Field private Long budget;
    @Field private Long revenue;
    @Field private Double runtime;

    @Field private String production_companies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
