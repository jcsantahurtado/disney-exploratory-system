package com.juansanta.disney.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NewUser {

    @NotNull
    @Size(max = 75)
    private String name;

    @NotNull
    @Size(max = 125)
    private String username;

    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    // By default, create a normal user
    // If I want an Admin user I must pass this roles field
    private List<String> userRoles = new ArrayList<>();

}
