package com.juansanta.disney.mapper;

import com.juansanta.disney.dto.GenreDto;
import com.juansanta.disney.entity.Genre;

public class GenreMapper {

    public static Genre mapToEntity(final GenreDto genreDTO, final Genre genre) {
        genre.setImageUrl(genreDTO.getImageUrl());
        genre.setName(genreDTO.getName());
        return genre;
    }

}
