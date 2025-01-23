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

class UsuarioControllerTest {

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
    
    public UsuarioControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() throws Exception {
        //Datos para ingresar 
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Leonardo");
        usuarioDto.setApellidos("Retto");
        usuarioDto.setUsuario("Lretto");
        usuarioDto.setPassword("123Retto");
        usuarioDto.setCorreo("Lretto@mail.com");
        usuarioDto.setTelefono("987123546");
        usuarioDto.setIdRol(1L);
        usuarioDto.setIdEstado(1L);

        //Mock de los datos relacionados
        Rol rolMock = new Rol();
        rolMock.setIdRol(1L);
        rolMock.setNombre("Admin");

        Estado estadoMock = new Estado();
        estadoMock.setIdEstado(1L);
        estadoMock.setNombre("Activo");

        //Mock de los comportamientos de los repositorios
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rolMock));
        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estadoMock));
        when(passwordEncoder.encode("123Retto")).thenReturn("encryptedPassword123");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setNombre("Leonardo");
        usuarioGuardado.setApellidos("Retto");
        usuarioGuardado.setUsuario("Lretto");
        usuarioGuardado.setPassword("encryptedPassword123");
        usuarioGuardado.setCorreo("Lretto@mail.com");
        usuarioGuardado.setTelefono("987123546");
        usuarioGuardado.setRol(rolMock);
        usuarioGuardado.setEstado(estadoMock);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        //Llamar al método del controlador
        Usuario resultado = usuarioController.crearUsuario(usuarioDto);

        //Verificaciones
        assertNotNull(resultado);
        assertEquals("Leonardo", resultado.getNombre(), "Nombre Incorrecto");
        assertEquals("Retto", resultado.getApellidos(), "Apellido Incorrecto");
        assertEquals("Lretto", resultado.getUsuario(), "Usuario Incorrecto");
        assertEquals("encryptedPassword123", resultado.getPassword(), "Contraseña incorrecta");
        assertEquals("Lretto@mail.com", resultado.getCorreo(), "Correo Incorrecto");
        assertEquals("987123546", resultado.getTelefono(), "Telefono Incorrecto");
        assertEquals(rolMock, resultado.getRol(), "Rol incorrecto");
        assertEquals(estadoMock, resultado.getEstado(), "Estado incorrecto");

        //Verificar que los métodos de los mocks fueron llamados
        verify(rolRepository).findById(1L);
        verify(estadoRepository).findById(1L);
        verify(passwordEncoder).encode("123Retto");
        verify(usuarioRepository).save(any(Usuario.class));
    }
}