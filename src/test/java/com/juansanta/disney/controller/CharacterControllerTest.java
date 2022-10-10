package com.juansanta.disney.controller;

import com.juansanta.disney.data.FactoryCharacterTestData;
import com.juansanta.disney.data.FactoryMessageTestData;
import com.juansanta.disney.dto.CharacterDto;
import com.juansanta.disney.dto.CharacterSearchDto;
import com.juansanta.disney.dto.Message;
import com.juansanta.disney.entity.Character;
import com.juansanta.disney.service.CharacterService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class CharacterControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private CharacterController controller;

    @Mock
    private CharacterService service;

    @BeforeEach
    void setUp() {
        mvc = standaloneSetup(controller).build();
    }


    @Test
    void shouldReturnACharacterAsJSONMessage() throws Exception {

        //Given
        Character expectedCharacter = FactoryCharacterTestData.getCharacter();

        //When
        when(service.existsCharacterById(anyLong())).thenReturn(true);
        when(service.get(anyLong())).thenReturn(expectedCharacter);

        //Then
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/characters/1")
                                //.param("id", "1")
                )
                //                                          Actual              Expected
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(expectedCharacter.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedCharacter
                        .getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl", Matchers.is(expectedCharacter
                        .getImageUrl())))
                .andExpect(status().isOk())

        ;

        verify(service, times(1)).get(anyLong());

    }

    @Test
    void mustReturnAMessageWhenNotFindingACharacterById() throws Exception {
        //Given
        Message expectedMessage = FactoryMessageTestData.getMessageNotExistsCharacter();

        //When
        when(service.existsCharacterById(anyLong())).thenReturn(false);

        //Then
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/characters/2")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(expectedMessage
                        .getMessage())))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    void shouldReturnAllCharactersFoundByName() throws Exception {
        //Given
        List<CharacterSearchDto> characterSearchDtoList = FactoryCharacterTestData
                .getCharacterSearchDtoList();

        CharacterSearchDto expectedCharacterSearchDto = FactoryCharacterTestData
                .getCharacterSearchDto();

        //When
        when(service.findAllByName(anyString())).thenReturn(characterSearchDtoList);

        //Then
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters")
                        .param("name", "characterName")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl",
                        Matchers.is(expectedCharacterSearchDto.getImageUrl())))

                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",
                        Matchers.is(expectedCharacterSearchDto.getName())))
        ;
    }

    @Test
    void shouldReturnAnEmptyListWhenNotFindingSearchedCharactersByName() throws Exception {
        //Given
        List<CharacterSearchDto> expectedResponse = FactoryCharacterTestData.getCharacterSearchDtoEmptyList();

        //When
        when(service.findAllByName(anyString())).thenReturn(expectedResponse);

        //Then
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters")
                        .param("name", "characterName")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(0)))
        ;
    }

    @Test
    void shouldReturnAllCharactersFound() throws Exception {
        //Given
        List<CharacterSearchDto> characterSearchDtoList = FactoryCharacterTestData
                .getCharacterSearchDtoList();

        CharacterSearchDto expectedCharacterSearchDto = FactoryCharacterTestData
                .getCharacterSearchDto();

        //When
        when(service.findAll()).thenReturn(characterSearchDtoList);

        //Then
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters")
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl",
                        Matchers.is(expectedCharacterSearchDto.getImageUrl())))

                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",
                        Matchers.is(expectedCharacterSearchDto.getName())))
        ;
    }

    @Test
    void mustCreateACharacter() throws Exception {
        //Given
        var expectedResponse = FactoryCharacterTestData.getCharacter();

        //When
        when(service.create(any(CharacterDto.class))).thenReturn(expectedResponse);

        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/characters")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(FactoryCharacterTestData.getJSONCharacter())
                )
        ;

        //Then
        verify(service).create(any(CharacterDto.class));
    }

    @Test
    void mustReturnOneMessagePerMissingField() throws Exception {
        //Given
        var expectedMessage = FactoryMessageTestData.getMessageMissingFieldUrl();

        //When


        //Then
        mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/characters")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(FactoryCharacterTestData.getJSONCharacterWithoutAnyField())
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(expectedMessage
                        .getMessage())))
        ;

    }

}