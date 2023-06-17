package com.juansanta.disney.data;

import com.juansanta.disney.dto.CharacterDto;
import com.juansanta.disney.dto.CharacterSearchDto;
import com.juansanta.disney.entity.Character;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FactoryCharacterTestData {

    public static Character getCharacter() {

        Character character = new Character();
        character.setId(1L);
        character.setImageUrl("https://bootify.io/");
        character.setName("James P. Sulley Sullivan");
        character.setAge(18);
        character.setWeight(347.45);
        character.setStory("He was a renowned scarer at Monsters, Inc.");
        character.setMovies(new HashSet<>());
        character.setDateCreated(OffsetDateTime.now());
        character.setLastUpdated(OffsetDateTime.now());

        return character;

    }

    public static CharacterDto getCharacterDto() {

        CharacterDto characterDto = new CharacterDto();
        characterDto.setImageUrl("https://bootify.io/");
        characterDto.setName("James P. Sulley Sullivan");
        characterDto.setAge(18);
        characterDto.setWeight(347.45);
        characterDto.setStory("He was a renowned scarer at Monsters, Inc.");

        return characterDto;

    }

    public static CharacterSearchDto getCharacterSearchDto() {

        CharacterSearchDto characterSearchDto = new CharacterSearchDto();
        characterSearchDto.setImageUrl("https://bootify.io/");
        characterSearchDto.setName("James P. Sulley Sullivan");

        return characterSearchDto;

    }

    public static List<CharacterSearchDto> getCharacterSearchDtoList() {
        CharacterSearchDto characterSearchDto = getCharacterSearchDto();
        return List.of(characterSearchDto);
    }

    public static List<CharacterSearchDto> getCharacterSearchDtoEmptyList() {
        return new ArrayList<>();
    }

    public static String getJSONCharacter(){
        return "{\n" +
                "    \"imageUrl\": \"https://bootify.io/\",\n" +
                "    \"name\": \"James P. Sulley Sullivan\",\n" +
                "    \"age\": 20,\n" +
                "    \"weight\": 60.2,\n" +
                "    \"story\": \"Esta es la historia del personaje\"\n" +
                "}";
    }

    public static String getJSONCharacterWithoutAnyField(){
        return "{\n" +
                //"    \"imageUrl\": \"https://bootify.io/\",\n" +
                "    \"name\": \"James P. Sulley Sullivan\",\n" +
                "    \"age\": 20,\n" +
                "    \"weight\": 60.2,\n" +
                "    \"story\": \"Esta es la historia del personaje\"\n" +
                "}";
    }

}
