package com.juansanta.disney.mapper;

import com.juansanta.disney.dto.MovieSearchDto;
import com.juansanta.disney.entity.Movie;

public class MovieMapper {

    public static MovieSearchDto mapToSearchDTO(final Movie movie, final MovieSearchDto movieSearchDTO) {
        movieSearchDTO.setImageUrl(movie.getImageUrl());
        movieSearchDTO.setTitle(movie.getTitle());
        movieSearchDTO.setCreationDate(movie.getCreationDate());
        return movieSearchDTO;
    }
}
