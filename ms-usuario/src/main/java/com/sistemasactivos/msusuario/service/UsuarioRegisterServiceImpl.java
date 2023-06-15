package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.Usuario;
import com.sistemasactivos.msusuario.model.UsuarioRegister;
import com.sistemasactivos.msusuario.repository.RolRepository;
import com.sistemasactivos.msusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRegisterServiceImpl implements UsuarioRegisterService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean registerUser(UsuarioRegister userRegistration) throws Exception {
        // Verificar si el correo electrónico ya está registrado
        if (usuarioRepository.findByEmail(userRegistration.getEmail()).isPresent()) {
            throw new Exception("El mail ya esta registrado.");
        }

        // Creo un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(userRegistration.getNombre());
        usuario.setEmail(userRegistration.getEmail());

        // Encriptar la contraseña utilizando BCrypt
        String encryptedPassword = passwordEncoder.encode(userRegistration.getPassword());
        usuario.setPassword(encryptedPassword);

        // Asignar el rol de "usuario" al usuario registrado
        rolRepository.findByNombre("usuario").ifPresent(usuarioRol -> usuario.getRoles().add(usuarioRol));

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        // Cargar el usuario recién registrado en el sistema de Spring Security
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
