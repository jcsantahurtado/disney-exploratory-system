package com.juansanta.disney.repository;

import com.juansanta.disney.entity.Genre;
import com.juansanta.disney.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsMovieByTitle(String title);

    Movie getReferenceByTitle(String title);

    List<Movie> findByTitleContainingIgnoreCase(String name);

    List<Movie> findAllByGenres(Genre genre);

}
