package com.example.java.service;

import com.example.java.model.Gardener;
import com.example.java.model.Usuario;
import com.example.java.repository.PlantRepository;
import com.example.java.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final PlantRepository plantRepository;

    private final String ROL_LECTURA = "ROLE_LECTURA";

    private final String ROL_GARDENER = "ROLE_GARDENER";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Usuario usuario = usuarioRepository.findByUsername(username);

       if(usuario == null) {
           throw new UsernameNotFoundException("LoadUserByUsername: Usuario no encontrado: " + username);
       }
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(usuario.getRol())))
                .build();
    }

    public Usuario saveUsuario(Usuario usuario){

        Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());

        if(usuarioExistente != null){
            throw new DataIntegrityViolationException("Usuario ya se encuentra registrado");
        }

        usuario.setRol(ROL_LECTURA);
        usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }


    public Usuario updateRolUsuario(Long id, String newRol){
        Usuario usuario = getUsuarioById(id);

        if(usuario == null){
            throw new UsernameNotFoundException("updateRolUsuario: Usuario no encontrado "+ id);
        }

        usuario.setRol(newRol);
        return usuarioRepository.save(usuario);
    }

    public void updateGardenerUserRol(Long id, Gardener gardener){
        Usuario usuario = getUsuarioById(id);

        if(usuario == null){
            throw new UsernameNotFoundException("updateGardenerUserRol: Usuario no encontrado "+ id);
        }

        usuario.setRol(ROL_GARDENER);
        usuario.setGardener(gardener);
        usuarioRepository.save(usuario);
    }

    @Transactional// SE CONCRETA LA OPERACION SI TODAS LAS OPERACIONES SALEN OK.
    public void deleteUsuario(Long id){
            Optional<Usuario> currentUsuario = usuarioRepository.findById(id);
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

            if(currentUsuario.isPresent()){

                if(currentUsername.equals(currentUsuario.get().getUsername())){
                    throw new IllegalArgumentException("deleteUsuario: No se puede eliminar el ususario actual en sección");
                }

                if (currentUsuario.get().getGardener() != null) {
                    plantRepository.deleteByGardener(currentUsuario.get().getGardener());
                }
            }

        usuarioRepository.deleteById(id);
    }


    public List<Usuario> getAllRegisteredUsuarios(){
        return usuarioRepository.findAll();
    }


    private Usuario getUsuarioById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el usuario: " + id));
    }

    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
