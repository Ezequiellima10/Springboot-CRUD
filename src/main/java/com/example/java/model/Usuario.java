package com.example.java.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    private String password;

    private String rol;

    private String name;

    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Gardener gardener;

}
