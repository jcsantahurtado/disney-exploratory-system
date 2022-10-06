package com.juansanta.disney.security.controller;

import com.juansanta.disney.dto.Message;
import com.juansanta.disney.security.dto.JwtDto;
import com.juansanta.disney.security.dto.LoginUser;
import com.juansanta.disney.security.dto.NewUser;
import com.juansanta.disney.security.jwt.JwtProvider;
import com.juansanta.disney.security.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    // Expect a json and convert it to class type NewUser
    @PostMapping("/register")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUser newUser,
                                        BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(new Message("Campos mal o email invalido"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByUsername(newUser.getUsername())){
            return new ResponseEntity<>(new Message("Ese nombre ya existe"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(newUser.getEmail())){
            return new ResponseEntity<>(new Message("Ese email ya existe"),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.create(newUser), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Message("Campos mal"), HttpStatus.BAD_REQUEST);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                                loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity<>(jwtDto, HttpStatus.OK);

    }
}