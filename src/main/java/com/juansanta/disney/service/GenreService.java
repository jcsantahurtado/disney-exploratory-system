package com.juansanta.disney.service;

import com.juansanta.disney.dto.GenreDto;
import com.juansanta.disney.entity.Genre;
import com.juansanta.disney.mapper.GenreMapper;
import com.juansanta.disney.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public void create(final GenreDto genreDTO) {
        final Genre genre = new Genre();
        GenreMapper.mapToEntity(genreDTO, genre);
        genreRepository.save(genre);
    }

    public boolean existsGenreByName(String name) {
        return genreRepository.existsGenreByName(name);
    }

}
