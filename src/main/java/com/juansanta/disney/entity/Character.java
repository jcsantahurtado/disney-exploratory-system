package com.juansanta.disney.entity;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Getter
@Setter
public class Character {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 205)
    private String imageUrl;

    @Column(length = 40)
    private String name;

    @Column
    private Integer age;

    @Column
    private Double weight;

    @Column
    private String story;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER)  // EAGER = fetch immediately
    @JsonIgnoreProperties("characters")
    private Set<Movie> movies = new HashSet<>();

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

    public void addMovie(Movie movie) {
        if (movies.contains(movie)) return;
        movies.add(movie);
    }

    public void deleteMovie(Movie movie) {
        if (!movies.contains(movie)) return;
        movies.remove(movie);
    }

}
