package com.libreria.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.libreria.models.Usuario;
import com.libreria.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository userRepository;

    public CustomUserDetailsService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        // Buscar el usuario por su nombre de usuario (campo 'usuario')
        Usuario user = userRepository.findByUsuario(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username"));

        // Crear un conjunto de autoridades (roles)
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Si el usuario tiene un rol, asignamos ese rol como autoridad
        if (user.getRol() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRol().getNombre())); // O 'getName' si ese es el método correcto en 'Rol'
        }

        // Devolver el UserDetails con la información del usuario y las autoridades (roles)
        return new org.springframework.security.core.userdetails.User(user.getUsuario(), user.getPassword(), authorities);
    }
}
