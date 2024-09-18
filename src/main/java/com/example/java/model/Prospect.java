package com.example.java.model;

import jakarta.persistence.*;
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

    private String name;

    private Double amount;

    private String unit;

    @ManyToMany(mappedBy = "associatedProspectus", fetch = FetchType.EAGER)
    private List<Plant> plants;
}
