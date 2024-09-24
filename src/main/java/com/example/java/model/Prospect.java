package com.example.java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prospect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank(message = "El nombre no puede ser vacia")
    private String name;

    @NotNull(message = "La cantidad no puede ser nula")
    private Double amount;

    @NotBlank(message = "La unidad no puede ser vacia")
    private String unit;

    @ManyToMany(mappedBy = "associatedProspectus", fetch = FetchType.EAGER)
    private List<Plant> plants;
}
