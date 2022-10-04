package com.juansanta.disney.service;

import com.juansanta.disney.dto.CharacterDto;
import com.juansanta.disney.entity.Character;
import com.juansanta.disney.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

// Notation to indicate that it is a Service
@Service
// Ensures that all required data is safe until the transaction is complete
@Transactional
public class CharacterService {

    // Dependency injection (creates an instance when required)
    @Autowired
    CharacterRepository characterRepository;

    // By default, the repository when extending JPA brings the default method
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public Character get(final Long id) {
        return characterRepository.findById(id).get();
    }

    public Optional<Character> getCharacterByName(final String name) {
        return characterRepository.findCharacterByName(name);
    }

    public Character create(final CharacterDto characterDto) {
        final Character character = new Character();
        mapToEntity(characterDto, character);
        return characterRepository.save(character);
    }

    public Character update(final Long id, final CharacterDto characterDto) {
        final Character character = characterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(characterDto, character);
        return characterRepository.save(character);
    }

    public void delete(Long id) {
        characterRepository.deleteById(id);
    }

    public boolean existsCharacterById(Long id) {
        return characterRepository.existsById(id);
    }

    public boolean existsCharacterByName(String name) {
        return characterRepository.existsCharacterByName(name);
    }

    private Character mapToEntity(final CharacterDto characterDTO, final Character character) {
        character.setImageUrl(characterDTO.getImageUrl());
        character.setName(characterDTO.getName());
        character.setAge(characterDTO.getAge());
        character.setWeight(characterDTO.getWeight());
        character.setStory(characterDTO.getStory());
        return character;
    }

}
