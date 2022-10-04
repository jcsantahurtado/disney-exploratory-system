package com.juansanta.disney.service;

import com.juansanta.disney.dto.GenreDTO;
import com.juansanta.disney.entity.Genre;
import com.juansanta.disney.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public void create(final GenreDTO genreDTO) {
        final Genre genre = new Genre();
        mapToEntity(genreDTO, genre);
        genreRepository.save(genre);
    }

    public boolean existsGenreByName(String name) {
        return genreRepository.existsGenreByName(name);
    }

    private Genre mapToEntity(final GenreDTO genreDTO, final Genre genre) {
        genre.setImageUrl(genreDTO.getImageUrl());
        genre.setName(genreDTO.getName());
        return genre;
    }

}
