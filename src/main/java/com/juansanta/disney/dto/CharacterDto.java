package com.juansanta.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CharacterDto {

    @Size(max = 205)
    private String imageUrl;

    @Size(max = 40)
    private String name;

    private Integer age;

    private Double weight;

    @Size(max = 255)
    private String story;

}
