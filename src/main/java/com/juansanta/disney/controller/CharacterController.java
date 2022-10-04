package com.juansanta.disney.controller;

import com.juansanta.disney.dto.CharacterDto;
import com.juansanta.disney.dto.Message;
import com.juansanta.disney.entity.Character;
import com.juansanta.disney.service.CharacterService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Notation to indicate that it is a controller of type Rest
@RestController
// Notation to indicate the context of our endpoint, that is /api/serviceName
@RequestMapping("/api/characters")
public class CharacterController {

     /* The name of the character is unique,
     in the creation and update the validation is done */


    // Dependency injection
    @Autowired
    CharacterService characterService;

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable final Long id) {
        if (!characterService.existsCharacterById(id))
            return new ResponseEntity(new Message("El personaje no existe"), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(characterService.get(id));
    }

    // The type of request is indicated as well as the name of the service, ex: @PostMapping
    // With the ? we tell it that it does not return any type of data
    // The body will be a JSON
    // Apache commons lang is used here
    // StringUtils import is import org.apache.commons.lang3.StringUtils;
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> createCharacter(@RequestBody @Valid final CharacterDto characterDto) {

        if (StringUtils.isBlank(characterDto.getImageUrl()))
            return new ResponseEntity<>(new Message("La URL de la imagen es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(characterDto.getName()))
            return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if (characterDto.getAge() < 0 || (Integer) characterDto.getAge() == null)
            return new ResponseEntity<>(new Message("La edad del personaje debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        if (characterDto.getWeight() < 0 || (Double) characterDto.getWeight() == null)
            return new ResponseEntity<>(new Message("El peso del personaje debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(characterDto.getStory()))
            return new ResponseEntity<>(new Message("La historia es obligatorio"), HttpStatus.BAD_REQUEST);

        if (characterService.existsCharacterByName(characterDto.getName()))
            return new ResponseEntity<>(new Message("Ya existe un personaje con ese nombre"), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(characterService.create(characterDto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharacter(@PathVariable final Long id,
                                             @RequestBody @Valid final CharacterDto characterDto) {

        if (!characterService.existsCharacterById(id))
            return new ResponseEntity<>(new Message("No existe el personaje"), HttpStatus.NOT_FOUND);

        if (characterService.existsCharacterByName(characterDto.getName())
                && characterService.getCharacterByName(characterDto.getName()).get().getId() != id)
            return new ResponseEntity<>(new Message("El nombre del personaje ya existe"), HttpStatus.NOT_FOUND);

        if (StringUtils.isBlank(characterDto.getName()))
            return new ResponseEntity<>(new Message("La URL de la imagen para el personaje es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(characterDto.getName()))
            return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if (characterDto.getAge() <= 0 || (Integer) characterDto.getAge() == null)
            return new ResponseEntity<>(new Message("La edad del personaje debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        if (characterDto.getWeight() < 0 || (Double) characterDto.getWeight() == null)
            return new ResponseEntity<>(new Message("El peso del personaje debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(characterDto.getStory()))
            return new ResponseEntity<>(new Message("La historia es obligatorio"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(characterService.update(id, characterDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<?> deleteCharacter(@PathVariable final Long id) {
        if (!characterService.existsCharacterById(id))
            return new ResponseEntity<>(new Message("No existe el personaje"), HttpStatus.NOT_FOUND);
        characterService.delete(id);
        return new ResponseEntity<>(new Message("Personaje eliminado"), HttpStatus.OK);
    }

}
