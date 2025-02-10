package com.libreria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.dto.CambioPasswordDto;
import com.libreria.dto.UsuarioDto;
import com.libreria.models.Estado;
import com.libreria.models.Rol;
import com.libreria.models.Usuario;
import com.libreria.repository.EstadoRepository;
import com.libreria.repository.RolRepository;
import com.libreria.repository.UsuarioRepository;

/*import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;*/
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiv1/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// Obtener lista de usuarios
	@GetMapping("/listar")
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	@GetMapping("/listar/{usuario}")
	public Usuario listarUsuarioPorNombre(@PathVariable String usuario) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuario(usuario);

		// Verifica si el usuario fue encontrado
		if (usuarioOptional.isPresent()) {
			return usuarioOptional.get();
		} else {
			throw new RuntimeException("Usuario no encontrado");
		}
	}

	// Crear Usuario
	@PostMapping("/add")
	public Usuario crearUsuario(@RequestBody UsuarioDto usuarioDTO) throws Exception {
		// Buscar rol y estado en la base de datos
		Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
				.orElseThrow(() -> new RuntimeException("Rol no encontrado"));

		Estado estado = estadoRepository.findById(usuarioDTO.getIdEstado())
				.orElseThrow(() -> new RuntimeException("Estado no encontrado"));

		// Crear nuevo usuario
		Usuario usuario = new Usuario();
		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setApellidos(usuarioDTO.getApellidos());
		usuario.setUsuario(usuarioDTO.getUsuario());

		// Encriptar la contraseña
		String encryptedPassword = passwordEncoder.encode(usuarioDTO.getPassword());
		usuario.setPassword(encryptedPassword);

		usuario.setCorreo(usuarioDTO.getCorreo());
		usuario.setTelefono(usuarioDTO.getTelefono());
		usuario.setRol(rol);
		usuario.setEstado(estado);

		return usuarioRepository.save(usuario);
	}

	// Actualizar usuario
	@PutMapping("/actualizar/{id}")
	public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDTO) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
				.orElseThrow(() -> new RuntimeException("Rol no encontrado"));

		Estado estado = estadoRepository.findById(usuarioDTO.getIdEstado())
				.orElseThrow(() -> new RuntimeException("Estado no encontrado"));

		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setApellidos(usuarioDTO.getApellidos());
		usuario.setUsuario(usuarioDTO.getUsuario());
		if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
		}
		usuario.setCorreo(usuarioDTO.getCorreo());
		usuario.setTelefono(usuarioDTO.getTelefono());
		usuario.setRol(rol);
		usuario.setEstado(estado);

		return usuarioRepository.save(usuario);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}

		usuarioRepository.deleteById(id);

		return ResponseEntity.ok("Usuario eliminado correctamente");
	}

	
	
	// ---------------------------CAMBIO DE CONTRASEÑA---------------------------

	@PutMapping("/cambiarPassword")
	public ResponseEntity<String> cambiarPassword(@RequestBody CambioPasswordDto cambioPasswordDTO) {
        Usuario usuario = usuarioRepository.findById(cambioPasswordDTO.getIdUsuario()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        // Verificar si la contraseña actual es correcta
        if (!passwordEncoder.matches(cambioPasswordDTO.getCurrentPassword(), usuario.getPassword())) {
            return ResponseEntity.badRequest().body("Contraseña actual incorrecta");
        }

        // Actualizar la contraseña encriptada
        usuario.setPassword(passwordEncoder.encode(cambioPasswordDTO.getNewPassword()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Contraseña cambiada exitosamente");
    }
}
