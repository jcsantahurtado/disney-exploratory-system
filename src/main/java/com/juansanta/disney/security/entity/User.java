package com.juansanta.disney.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Class for the database
 */
@Entity
@Table(name = "\"USER\"")  // @Table(name = "\"user\"")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    //Id de la tabla
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID Auto Increment
    private Long id;

    @Column(nullable = false, length = 75)
    private String name;

    @Column(nullable = false, unique = true, length = 125)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // many-to-many relationship
    // A user can have MANY roles and a role can BELONG to multiple users
    // Intermediate table that has two fields that will have userId and roleId
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // join columns references the column that references this
    // That is, the user_role table will have a field called user_id
    // inverseJoinColumns = the inverse, reference role
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> userRoleRoles;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

    // Constructor without ID or Roles
    public User(@NotNull String name,
                @NotNull String username,
                @NotNull String email,
                @NotNull String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}