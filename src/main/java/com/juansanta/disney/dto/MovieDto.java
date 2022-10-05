package com.juansanta.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class MovieDto {

    @Size(max = 205)
    private String imageUrl;

    @Size(max = 75)
    private String title;

    private LocalDate creationDate;

    private Integer rating;

    private List<Long> movieCharacters;

    private List<Long> movieGenres;

}
