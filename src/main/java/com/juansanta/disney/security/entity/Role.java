package com.juansanta.disney.security.entity;

import com.juansanta.disney.security.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    // It is indicated that it will be an Enum of type String
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany(mappedBy = "userRoleRoles")
    private Set<User> userRoleUsers;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

}