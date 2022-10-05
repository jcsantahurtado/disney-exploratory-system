package com.juansanta.disney.controller;

import com.juansanta.disney.dto.Message;
import com.juansanta.disney.dto.MovieDto;
import com.juansanta.disney.service.MovieService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    // The type of request is indicated as well as the name of the service, ex: @PostMapping
    // With the ? we tell it that it does not return any type of data
    // The body will be a JSON
    // Apache commons lang is used here
    // StringUtils import is import org.apache.commons.lang3.StringUtils;
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> createMovie(@Valid @RequestBody final MovieDto movieDto,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error campos");
        }

        if (StringUtils.isBlank(movieDto.getImageUrl()))
            return new ResponseEntity<>(new Message("La URL de la imagen para la película es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(movieDto.getTitle()))
            return new ResponseEntity<>(new Message("El título es obligatorio"), HttpStatus.BAD_REQUEST);

        if (movieDto.getCreationDate() == null)
            return new ResponseEntity<>(new Message("La fecha de creación es requerida"), HttpStatus.BAD_REQUEST);

        if (movieDto.getRating() < 1 || movieDto.getRating() > 5)
            return new ResponseEntity<>(new Message("La calificación debe ser del 1 al 5"), HttpStatus.BAD_REQUEST);

        if (movieService.existsMovieByTitle(movieDto.getTitle()))
            return new ResponseEntity<>(new Message("Ya existe una película con ese título"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(movieService.create(movieDto), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharacter(@PathVariable final Long id,
                                             @RequestBody @Valid final MovieDto movieDto) {

        if (!movieService.existsMovieById(id))
            return new ResponseEntity<>(new Message("No existe la película"), HttpStatus.NOT_FOUND);

        if (movieService.existsMovieByTitle(movieDto.getTitle())
                && movieService.getMovieByTitle(movieDto.getTitle()).get().getId() != id)
            return new ResponseEntity<>(new Message("El título de la película ya está registrado"), HttpStatus.NOT_FOUND);

        if (StringUtils.isBlank(movieDto.getTitle()))
            return new ResponseEntity<>(new Message("El título de la película es obligatoria"), HttpStatus.BAD_REQUEST);

        if (movieDto.getCreationDate() == null)
            return new ResponseEntity<>(new Message("La fecha de creación es requerida"), HttpStatus.BAD_REQUEST);

        if (movieDto.getRating() < 1 || movieDto.getRating() > 5)
            return new ResponseEntity<>(new Message("La calificación debe ser del 1 al 5"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(movieService.update(id, movieDto), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<?> deleteCharacter(@PathVariable final Long id) {
        if (!movieService.existsMovieById(id))
            return new ResponseEntity<>(new Message("No existe película con ese ID"), HttpStatus.NOT_FOUND);
        movieService.delete(id);
        return new ResponseEntity<>(new Message("Película eliminada"), HttpStatus.OK);
    }

}
