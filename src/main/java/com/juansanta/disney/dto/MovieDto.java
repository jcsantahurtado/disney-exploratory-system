package com.juansanta.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class MovieDto {

    private Long id;

    @Size(max = 205)
    private String imageUrl;

    @Size(max = 75)
    private String title;

    private LocalDate creationDate;

    private Integer rating;

    private List<Long> movieCharacters;

    private List<Long> movieGenres;

    public MovieDto(Long id, String imageUrl, String title, LocalDate creationDate, Integer rating) {
    }
}
