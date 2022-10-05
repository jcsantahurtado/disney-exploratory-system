package com.juansanta.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
public class MovieSearchDto {

    @Size(max = 205)
    private String imageUrl;

    @Size(max = 75)
    private String title;

    private LocalDate creationDate;

}
