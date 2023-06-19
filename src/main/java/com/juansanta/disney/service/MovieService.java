package com.juansanta.disney.service;

import com.juansanta.disney.dto.MovieDto;
import com.juansanta.disney.dto.MovieSearchDto;
import com.juansanta.disney.entity.Character;
import com.juansanta.disney.entity.Genre;
import com.juansanta.disney.entity.Movie;
import com.juansanta.disney.mapper.MovieMapper;
import com.juansanta.disney.repository.CharacterRepository;
import com.juansanta.disney.repository.GenreRepository;
import com.juansanta.disney.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

// Notation to indicate that it is a Service
@Service
// Ensures that all required data is safe until the transaction is complete
@Transactional
public class MovieService {

    // Dependency injection (creates an instance when required)
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GenreRepository genreRepository;

    public Movie get(final Long id) {
        return movieRepository.findById(id).get();
    }

    public Movie getMovieByTitle(final String title) {
        return movieRepository.getReferenceByTitle(title);
    }

    public Movie create(final MovieDto movieDto) {
        final Movie movie = new Movie();

        final List<Character> movieCharacters = characterRepository.findAllById(
                movieDto.getMovieCharacters() == null ? Collections.emptyList() : movieDto.getMovieCharacters());

        final List<Genre> movieGenres = genreRepository.findAllById(
                movieDto.getMovieGenres() == null ? Collections.emptyList() : movieDto.getMovieGenres());

        MovieMapper.mapToEntity(movieDto, movie);
        return movieRepository.save(movie);
    }

    public MovieDto update(final Long id, final MovieDto movie) {
        final Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existingMovie.setImageUrl(movie.getImageUrl());
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setCreationDate(movie.getCreationDate());
        existingMovie.setRating(movie.getRating());

        Movie updatedMovie = movieRepository.save(existingMovie);

        return MovieMapper.mapToDto(updatedMovie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    public List<MovieSearchDto> findAllByName(String name) {
        return movieRepository.findByTitleContainingIgnoreCase(name)
                .stream()
                .map(movie -> MovieMapper.mapToSearchDTO(movie, new MovieSearchDto()))
                .collect(Collectors.toList());
    }

    public List<MovieSearchDto> findAllByIdGenre(Long idGenre) {
        Genre genre = genreRepository.getReferenceById(idGenre);
        return movieRepository.findAllByGenres(genre)
                .stream()
                .map(movie -> MovieMapper.mapToSearchDTO(movie, new MovieSearchDto()))
                .collect(Collectors.toList());
    }

    public List<MovieSearchDto> findAllInOrder(String order) {

        List<Movie> movies;

        switch(order) {
            case "ASC":
                movies = movieRepository.findAll(Sort.by("id"));
                break;
            case "DESC":
                movies = movieRepository.findAll(Sort.by("id").descending());
                break;
            default:
                movies = new ArrayList<>();
        }

        return movies
                .stream()
                .map(movie -> MovieMapper.mapToSearchDTO(movie, new MovieSearchDto()))
                .collect(Collectors.toList());

    }

    public List<MovieSearchDto> findAll() {
        return movieRepository.findAll(Sort.by("id"))
                .stream()
                .map(movie -> MovieMapper.mapToSearchDTO(movie, new MovieSearchDto()))
                .collect(Collectors.toList());
    }

    public void addCharacterToMovie(Long idMovie, Long idCharacter) {

        Movie movie = movieRepository.getReferenceById(idMovie);
        Character character = characterRepository.getReferenceById(idCharacter);

        movie.addCharacter(character);
        character.addMovie(movie);

        movieRepository.save(movie);
        characterRepository.save(character);

    }

    public void deleteCharacterFromMovie(Long idMovie, Long idCharacter) {

        Movie movie = movieRepository.getReferenceById(idMovie);
        Character character = characterRepository.getReferenceById(idCharacter);

        movie.deleteCharacter(character);
        character.deleteMovie(movie);

        movieRepository.save(movie);
        characterRepository.save(character);

    }

    public boolean existsMovieById(Long id) {
        return movieRepository.existsById(id);
    }

    public boolean existsMovieByTitle(String title) {
        return movieRepository.existsMovieByTitle(title);
    }

}
