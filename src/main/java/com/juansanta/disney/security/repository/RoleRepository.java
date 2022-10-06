package com.juansanta.disney.security.repository;

import com.juansanta.disney.security.entity.Role;
import com.juansanta.disney.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
// Notation indicating that it is a repository
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);

}
