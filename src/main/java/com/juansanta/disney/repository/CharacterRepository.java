package com.juansanta.disney.repository;

import com.juansanta.disney.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Notation to indicate that it is a Repository
@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    // With @Repository the main methods select, create, update, delete are indicated

    // JpaRepositoy allows searches by field according to the entity
    Optional<Character> findCharacterByName(String name);
    boolean existsCharacterByName(String name);

}
