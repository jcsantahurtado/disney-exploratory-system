package com.juansanta.disney.mapper;

import com.juansanta.disney.dto.MovieDto;
import com.juansanta.disney.dto.MovieSearchDto;
import com.juansanta.disney.entity.Character;
import com.juansanta.disney.entity.Genre;
import com.juansanta.disney.entity.Movie;
import com.juansanta.disney.repository.CharacterRepository;
import com.juansanta.disney.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MovieMapper {

    @Autowired
    static
    CharacterRepository characterRepository;

    @Autowired
    static
    GenreRepository genreRepository;

    public static MovieDto mapToDto(Movie movie){
        MovieDto movieDto = new MovieDto(
                movie.getId(),
                movie.getImageUrl(),
                movie.getTitle(),
                movie.getCreationDate(),
                movie.getRating()
        );
        return movieDto;
    }

    public static Movie mapToEntity(final MovieDto movieDto, final Movie movie) {

        movie.setImageUrl(movieDto.getImageUrl());
        movie.setTitle(movieDto.getTitle());
        movie.setCreationDate(movieDto.getCreationDate());
        movie.setRating(movieDto.getRating());

        final List<Character> movieCharacters = characterRepository.findAllById(
                movieDto.getMovieCharacters() == null ? Collections.emptyList() : movieDto.getMovieCharacters());

        if (movieCharacters.size() != (movieDto.getMovieCharacters() == null ? 0 : movieDto.getMovieCharacters().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of movieCharacters not found");
        }
        if (movie.getCharacters() == null) {
            movie.setCharacters(new HashSet<>(movieCharacters));
            // movie.setCharacters(movieCharacters.stream().collect(Collectors.toSet()));
        }

        final List<Genre> movieGenres = genreRepository.findAllById(
                movieDto.getMovieGenres() == null ? Collections.emptyList() : movieDto.getMovieGenres());

        if (movieGenres.size() != (movieDto.getMovieGenres() == null ? 0 : movieDto.getMovieGenres().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of movieGenres not found");
        }
        if (movie.getGenres() == null) {
            movie.setGenres(new HashSet<>(movieGenres));
            //movie.setMovieGenreGenres(movieGenres.stream().collect(Collectors.toSet()));
        }

        return movie;

    }

    public static MovieSearchDto mapToSearchDTO(final Movie movie, final MovieSearchDto movieSearchDTO) {
        movieSearchDTO.setImageUrl(movie.getImageUrl());
        movieSearchDTO.setTitle(movie.getTitle());
        movieSearchDTO.setCreationDate(movie.getCreationDate());
        return movieSearchDTO;
    }
}
