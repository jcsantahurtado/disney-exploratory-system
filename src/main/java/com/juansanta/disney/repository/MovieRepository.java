package com.juansanta.disney.repository;

import com.juansanta.disney.entity.Genre;
import com.juansanta.disney.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
