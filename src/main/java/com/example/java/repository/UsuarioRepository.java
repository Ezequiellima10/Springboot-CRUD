package com.example.java.repository;

import com.example.java.model.Gardener;
import com.example.java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByGardener(Gardener gardener);

    List<Usuario> findAllByGardenerIsNotNull();


}
