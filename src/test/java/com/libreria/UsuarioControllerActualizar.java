package com.libreria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.libreria.controllers.UsuarioController;
import com.libreria.dto.UsuarioDto;
import com.libreria.models.Estado;
import com.libreria.models.Rol;
import com.libreria.models.Usuario;
import com.libreria.repository.EstadoRepository;
import com.libreria.repository.RolRepository;
import com.libreria.repository.UsuarioRepository;

import java.util.Optional;

class UsuarioControllerActualizar {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private EstadoRepository estadoRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UsuarioController usuarioController;
    
    public UsuarioControllerActualizar() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testActualizarUsuario() {
        // Datos de entrada
        Long idUsuario = 1L;

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Leonardo");
        usuarioDto.setApellidos("Retto");
        usuarioDto.setUsuario("LrettoUpdated");
        usuarioDto.setPassword("123123");
        usuarioDto.setCorreo("LeronadoR@gmail.com");
        usuarioDto.setTelefono("987654321");
        usuarioDto.setIdRol(2L);
        usuarioDto.setIdEstado(2L);

        // Base de datos simulada
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(idUsuario);
        usuarioExistente.setNombre("Jose");
        usuarioExistente.setApellidos("Retto");
        usuarioExistente.setUsuario("Jretto");
        usuarioExistente.setPassword("123456789");
        usuarioExistente.setCorreo("Lreto@gmail.com");
        usuarioExistente.setTelefono("987654321");

        Rol rolMock = new Rol();
        rolMock.setIdRol(2L);
        rolMock.setNombre("Almacenero");

        Estado estadoMock = new Estado();
        estadoMock.setIdEstado(2L);
        estadoMock.setNombre("inactivo");

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuarioExistente));
        when(rolRepository.findById(2L)).thenReturn(Optional.of(rolMock));
        when(estadoRepository.findById(2L)).thenReturn(Optional.of(estadoMock));
        when(passwordEncoder.encode("123123")).thenReturn("encryptedNewPassword123123");


        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setIdUsuario(idUsuario);
        usuarioActualizado.setNombre("Leonardo");
        usuarioActualizado.setApellidos("Retto");
        usuarioActualizado.setUsuario("LrettoUpdated");
        usuarioActualizado.setPassword("encryptedNewPassword123123");
        usuarioActualizado.setCorreo("Lreto@gmail.com");
        usuarioActualizado.setTelefono("987654321");
        usuarioActualizado.setRol(rolMock);
        usuarioActualizado.setEstado(estadoMock);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        //usamos el controlador y el metodo para actualizar
        Usuario resultado = usuarioController.actualizarUsuario(idUsuario, usuarioDto);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Leonardo", resultado.getNombre());
        assertEquals("Retto", resultado.getApellidos());
        assertEquals("LrettoUpdated", resultado.getUsuario());
        assertEquals("encryptedNewPassword123123", resultado.getPassword());

        assertEquals("Lreto@gmail.com", resultado.getCorreo());
        assertEquals("987654321", resultado.getTelefono());
        assertEquals(rolMock, resultado.getRol());
        assertEquals(estadoMock, resultado.getEstado());
	}

}
