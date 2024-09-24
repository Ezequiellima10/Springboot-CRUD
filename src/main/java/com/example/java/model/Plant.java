package com.example.java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank(message = "La especie no puede ser vacia")
    private String species;

    @NotBlank(message = "El color de la hoja no puede ser vacio")
    private String leafColor;

    @NotNull(message = "La fecha no puede ser vacia")
    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "gardener_id")
    @ToString.Exclude // SOLUCION LOOP INFINITO LOMBOK
    @NotNull(message = "El jardinero no puede ser vacia")
    private Gardener gardener;

    @ManyToMany
    @JoinTable(name = "Plant_Prospectus",
        joinColumns = @JoinColumn(name = "plant_id"),
        inverseJoinColumns = @JoinColumn(name = "prospect_id"))
    private List<Prospect> associatedProspectus;
}
