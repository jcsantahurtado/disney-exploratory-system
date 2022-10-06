package com.juansanta.disney.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class LoginUser {

    @NotNull
    @Size(max = 125)
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;

}
