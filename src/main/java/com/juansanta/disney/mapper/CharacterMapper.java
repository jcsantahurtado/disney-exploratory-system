package com.juansanta.disney.mapper;

import com.juansanta.disney.dto.CharacterDto;
import com.juansanta.disney.dto.CharacterSearchDto;
import com.juansanta.disney.entity.Character;

public class CharacterMapper {

    // Convert Character JPA Entity into CharacterDto
    public static CharacterDto mapToDto(Character character){
        CharacterDto characterDto = new CharacterDto(
                character.getImageUrl(),
                character.getName(),
                character.getAge(),
                character.getWeight(),
                character.getStory()
        );
        return characterDto;
    }

    // Convert CharacterDto into Character JPA Entity
    public static Character mapToEntity(CharacterDto characterDto){
        Character character = new Character(
                characterDto.getImageUrl(),
                characterDto.getName(),
                characterDto.getAge(),
                characterDto.getWeight(),
                characterDto.getStory()
        );
        return character;
    }

    public static CharacterSearchDto mapToSearchDto(Character character) {
        CharacterSearchDto characterSearchDto = new CharacterSearchDto();
        characterSearchDto.setImageUrl(character.getImageUrl());
        characterSearchDto.setName(character.getName());
        return characterSearchDto;
    }
}
