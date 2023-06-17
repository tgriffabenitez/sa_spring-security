package com.sistemasactivos.msusuario.service;

import com.sistemasactivos.msusuario.model.Usuario;
import com.sistemasactivos.msusuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de UserDetailsService para cargar los detalles del usuario a partir del nombre de usuario (email).
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles del usuario a partir del nombre de usuario (email).
     *
     * @param email El nombre de usuario (email) del usuario.
     * @return Los detalles del usuario.
     * @throws UsernameNotFoundException Si no se encuentra el usuario con el nombre de usuario (email) especificado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository
                .findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Crear una instancia de UserDetailsImpl con el usuario encontrado
        return new UserDetailsImpl(user);
    }
}
