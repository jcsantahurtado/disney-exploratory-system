package com.juansanta.disney.controller;

import com.juansanta.disney.dto.GenreDto;
import com.juansanta.disney.dto.Message;
import com.juansanta.disney.service.GenreService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {

    @Autowired
    GenreService genreService;

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> createGenre(@RequestBody @Valid final GenreDto genreDTO) {

        if (StringUtils.isBlank(genreDTO.getImageUrl()))
            return new ResponseEntity<>(new Message("La URL de la imagen es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(genreDTO.getName()))
            return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if (genreService.existsGenreByName(genreDTO.getName()))
            return new ResponseEntity<>(new Message("Ya existe un género con ese nombre"), HttpStatus.BAD_REQUEST);

        genreService.create(genreDTO);
        return ResponseEntity.ok(new Message("Género creado"));
    }

}
