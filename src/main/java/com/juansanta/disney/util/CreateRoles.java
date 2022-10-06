package com.juansanta.disney.util;

/*import com.disney_exploratory_system.security.entity.Role;
import com.disney_exploratory_system.security.enums.RoleName;
import com.disney_exploratory_system.security.service.RoleService;*/
import com.juansanta.disney.security.entity.Role;
import com.juansanta.disney.security.enums.RoleName;
import com.juansanta.disney.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Comment or delete class after the first run of the application
@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
        Role roleUser = new Role(RoleName.ROLE_USER);
        roleService.save(roleAdmin);
        roleService.save(roleUser);
    }
}