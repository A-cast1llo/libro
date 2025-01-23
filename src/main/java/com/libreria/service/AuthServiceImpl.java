package com.libreria.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.dto.LoginDto;
import com.libreria.repository.UsuarioRepository;
import com.libreria.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UsuarioRepository userRepository,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        var usuario = userRepository.findByUsuario(loginDto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginDto.getPassword(), usuario.getPassword())) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ContraseÃ±a incorrecta");
        }

        if (usuario.getEstado().getIdEstado() != 1) {
            throw new RuntimeException("Usuario Inactivo");
        }
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsuario(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        return jwtTokenProvider.generateToken(authentication);
    }
}