package com.juansanta.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
public class CharacterSearchDto {

    @Size(max = 205)
    private String imageUrl;

    @Size(max = 40)
    private String name;

}
