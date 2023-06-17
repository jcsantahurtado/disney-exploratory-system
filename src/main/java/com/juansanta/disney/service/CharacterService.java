package com.juansanta.disney.service;

import com.juansanta.disney.dto.CharacterDto;
import com.juansanta.disney.dto.CharacterSearchDto;
import com.juansanta.disney.entity.Character;
import com.juansanta.disney.entity.Movie;
import com.juansanta.disney.mapper.CharacterMapper;
import com.juansanta.disney.repository.CharacterRepository;
import com.juansanta.disney.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

// Notation to indicate that it is a Service
@Service
// Ensures that all required data is safe until the transaction is complete
@Transactional
public class CharacterService {

    // Dependency injection (creates an instance when required)
    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    MovieRepository movieRepository;

    // By default, the repository when extending JPA brings the default method
    public List<CharacterSearchDto> findAll() {
        return characterRepository.findAll(Sort.by("id"))
                .stream()
                .map(character -> CharacterMapper.mapToSearchDto(character))
                .collect(Collectors.toList());
    }

    public Character get(final Long id) {
        return characterRepository.findById(id).get();
    }

    public Character getCharacterByName(final String name) {
        return characterRepository.getReferenceByName(name);
    }

    public CharacterDto create(CharacterDto characterDto) {

        // Convert CharacterDto into Character JPA Entity
        Character character = CharacterMapper.mapToEntity(characterDto);

        // Convert Character JPA entity to CharacterDto
        Character savedCharacter = characterRepository.save(character);

        // Convert User JPA entity to UserDto
        CharacterDto savedCharacterDto = CharacterMapper.mapToDto(savedCharacter);

        return savedCharacterDto;
    }

    public Character update(final Long id, final CharacterDto characterDto) {
        final Character character = characterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CharacterMapper.mapToEntity(characterDto);
        return characterRepository.save(character);
    }

    public void delete(Long id) {
        characterRepository.deleteById(id);
    }

    public List<CharacterSearchDto> findAllByName(final String name) {
        return characterRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(character -> CharacterMapper.mapToSearchDto(character))
                .collect(Collectors.toList());
    }

    public List<CharacterSearchDto> findAllByAge(final Integer age) {
        return characterRepository.findAllByAge(age)
                .stream()
                .map(character -> CharacterMapper.mapToSearchDto(character))
                .collect(Collectors.toList());
    }

    public List<CharacterSearchDto> findAllByIdMovie(final Long idMovie) {
        Movie movie = movieRepository.getReferenceById(idMovie);
        return characterRepository.findAllByMovies(movie)
                .stream()
                .map(character -> CharacterMapper.mapToSearchDto(character))
                .collect(Collectors.toList());
    }

    public boolean existsCharacterById(Long id) {
        return characterRepository.existsById(id);
    }

    public boolean existsCharacterByName(String name) {
        return characterRepository.existsCharacterByName(name);
    }

}
