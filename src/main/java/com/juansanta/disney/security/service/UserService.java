package com.juansanta.disney.security.service;

import com.juansanta.disney.dto.Message;
import com.juansanta.disney.security.dto.NewUser;
import com.juansanta.disney.security.entity.Role;
import com.juansanta.disney.security.entity.User;
import com.juansanta.disney.security.enums.RoleName;
import com.juansanta.disney.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public Object create(NewUser userDTO) throws IOException {

        final User user = new User();
        mapToEntity(userDTO, user);

        userRepository.save(user);

        return new Message("Se ha registrado con exito!");

    }

    private User mapToEntity(final NewUser userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        final List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        if(userDTO.getUserRoles().contains("admin"))
            userRoles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        user.setUserRoleRoles(new HashSet<>(userRoles));

        return user;

    }

}
