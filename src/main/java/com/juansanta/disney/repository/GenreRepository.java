package com.juansanta.disney.repository;

import com.juansanta.disney.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsGenreByName(String name);

}
