package com.example.java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gardener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank(message = "La especialidad no puede ser vacia")
    private String specialty;

    @NotBlank(message = "El mail no puede ser vacio")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El formato del email no es válido")
    private String email;

    @OneToMany(mappedBy = "gardener", fetch = FetchType.EAGER)
    private List<Plant> plants;

    @OneToOne(mappedBy = "gardener", cascade = CascadeType.ALL)
    private Usuario usuario;
}
