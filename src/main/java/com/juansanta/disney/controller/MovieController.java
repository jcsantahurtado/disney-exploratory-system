package com.juansanta.disney.controller;

import com.juansanta.disney.dto.Message;
import com.juansanta.disney.dto.MovieDto;
import com.juansanta.disney.dto.MovieSearchDto;
import com.juansanta.disney.entity.Movie;
import com.juansanta.disney.service.CharacterService;
import com.juansanta.disney.service.MovieService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

// Notation to indicate that it is a controller of type Rest
@RestController
// Notation to indicate the context of our endpoint, that is /api/serviceName
@RequestMapping("/api/movies")
public class MovieController {

     /* The name of the character is unique,
     in the creation and update the validation is done */


    // Dependency injection
    @Autowired
    MovieService movieService;

    @Autowired
    CharacterService characterService;

    @GetMapping
    public ResponseEntity<List<MovieSearchDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable final Long id) {
        if (!movieService.existsMovieById(id))
            return new ResponseEntity(new Message("La película con ese ID no existe"),
                    HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(movieService.get(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<MovieSearchDto>> getAllMoviesByName(@RequestParam(required = false)
                                                                       final String name) {
        return ResponseEntity.ok(movieService.findAllByName(name));
    }

    @GetMapping(params = "idGenre")
    public ResponseEntity<List<MovieSearchDto>> getAllMoviesByIdMovie(@RequestParam(required = false)
                                                                          final Long idGenre) {
        return ResponseEntity.ok(movieService.findAllByIdGenre(idGenre));
    }

    @GetMapping(params = "order")
    public ResponseEntity<List<MovieSearchDto>> getAllMoviesInOrder(@RequestParam(required = false)
                                                                        final String order) {
        return ResponseEntity.ok(movieService.findAllInOrder(order));
    }

    // The type of request is indicated as well as the name of the service, ex: @PostMapping
    // With the ? we tell it that it does not return any type of data
    // The body will be a JSON
    // Apache commons lang is used here
    // StringUtils import is import org.apache.commons.lang3.StringUtils;
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> createMovie(@Valid @RequestBody final MovieDto movieDto,
                                         BindingResult bindingResult) {

        if (StringUtils.isBlank(movieDto.getImageUrl()))
            return new ResponseEntity<>(new Message("La URL de la imagen para la película es obligatoria"),
                    HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(movieDto.getTitle()))
            return new ResponseEntity<>(new Message("El título es obligatorio"),
                    HttpStatus.BAD_REQUEST);

        if (movieDto.getCreationDate() == null)
            return new ResponseEntity<>(new Message("La fecha de creación es requerida"),
                    HttpStatus.BAD_REQUEST);

        if (movieDto.getRating() < 1 || movieDto.getRating() > 5)
            return new ResponseEntity<>(new Message("La calificación debe ser del 1 al 5"),
                    HttpStatus.BAD_REQUEST);

        if (movieService.existsMovieByTitle(movieDto.getTitle()))
            return new ResponseEntity<>(new Message("Ya existe una película con ese título"),
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(movieService.create(movieDto), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable final Long id,
                                         @RequestBody @Valid final MovieDto movieDto) {

        if (!movieService.existsMovieById(id))
            return new ResponseEntity<>(new Message("No existe la película"),
                    HttpStatus.NOT_FOUND);

        if (movieService.existsMovieByTitle(movieDto.getTitle())
                && !Objects.equals(movieService.getMovieByTitle(movieDto.getTitle()).getId(), id))
            return new ResponseEntity<>(new Message("El título de la película ya está registrado"),
                    HttpStatus.NOT_FOUND);

        if (StringUtils.isBlank(movieDto.getTitle()))
            return new ResponseEntity<>(new Message("El título de la película es obligatoria"),
                    HttpStatus.BAD_REQUEST);

        if (movieDto.getCreationDate() == null)
            return new ResponseEntity<>(new Message("La fecha de creación es requerida"),
                    HttpStatus.BAD_REQUEST);

        if (movieDto.getRating() < 1 || movieDto.getRating() > 5)
            return new ResponseEntity<>(new Message("La calificación debe ser del 1 al 5"),
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(movieService.update(id, movieDto), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<?> deleteMovie(@PathVariable final Long id) {
        if (!movieService.existsMovieById(id))
            return new ResponseEntity<>(new Message("No existe película con ese ID"), HttpStatus.NOT_FOUND);
        movieService.delete(id);
        return new ResponseEntity<>(new Message("Película eliminada"), HttpStatus.OK);
    }

    @PostMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<Message> addCharacterToMovie(@PathVariable final Long idMovie,
                                                       @PathVariable final Long idCharacter) {

        if (!movieService.existsMovieById(idMovie))
            return new ResponseEntity<>(new Message("No existe película con ese ID"), HttpStatus.NOT_FOUND);

        if (!characterService.existsCharacterById(idCharacter))
            return new ResponseEntity<>(new Message("No existe personaje con ese ID"), HttpStatus.NOT_FOUND);

        movieService.addCharacterToMovie(idMovie, idCharacter);
        return new ResponseEntity<>(new Message("El personaje fue agregado a la película"), HttpStatus.OK);

    }

    @DeleteMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<Message> deleteCharacterFromMovie(@PathVariable final Long idMovie,
                                                            @PathVariable final Long idCharacter) {

        if (!movieService.existsMovieById(idMovie))
            return new ResponseEntity<>(new Message("No existe película con ese ID"), HttpStatus.NOT_FOUND);

        if (!characterService.existsCharacterById(idCharacter))
            return new ResponseEntity<>(new Message("No existe personaje con ese ID"), HttpStatus.NOT_FOUND);

        movieService.deleteCharacterFromMovie(idMovie, idCharacter);
        return new ResponseEntity<>(new Message("El personaje fue removido de la película"), HttpStatus.OK);

    }

}
