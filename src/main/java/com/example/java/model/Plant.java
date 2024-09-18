package com.example.java.model;

import jakarta.persistence.*;
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

    private String species;

    private String leafColor;

    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "gardener_id")
    @ToString.Exclude // SOLUCION LOOP INFINITO LOMBOK
    private Gardener gardener;

    @ManyToMany
    @JoinTable(name = "Plant_Prospectus",
        joinColumns = @JoinColumn(name = "plant_id"),
        inverseJoinColumns = @JoinColumn(name = "prospect_id"))
    private List<Prospect> associatedProspectus;
}
