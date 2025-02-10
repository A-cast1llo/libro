package com.libreria;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.libreria.controllers.UsuarioController;
import com.libreria.repository.UsuarioRepository;

public class UsuarioEliminarControllerTest {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioController usuarioController;
	
	public UsuarioEliminarControllerTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void eliminarUsuario_Exito() {
		// Id que deseamos eliminar 
		Long userId = 6L;

		// Para esta prueba, el usuario con id 6 existirá en nuestra simulación
		
		when(usuarioRepository.existsById(6L)).thenReturn(true);

		usuarioController.eliminarUsuario(userId);

	}
}
