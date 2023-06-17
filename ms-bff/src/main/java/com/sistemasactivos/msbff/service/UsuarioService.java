package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.dto.UsuarioDTO;
import com.sistemasactivos.msbff.utils.CacheUtils;
import com.sistemasactivos.msbff.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Servicio para operaciones relacionadas con usuarios.
 */
@Service
public class UsuarioService {
    @Autowired
    @Qualifier("signInWebClient")
    private WebClient webClient;

    @Autowired
    private CacheUtils cacheUtils;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Recupera una lista de usuarios utilizando una solicitud HTTP del cliente.
     * Se requiere que el usuario tenga el rol de administrador para acceder a este endpoint.
     *
     * @param request La solicitud HTTP del cliente.
     * @return Un flujo de objetos UsuarioDTO que representa la lista de usuarios.
     */
    public Flux<UsuarioDTO> findAll(ServerHttpRequest request) {
        // Del header obtengo el token y el rol del usuario
        HttpHeaders requestHeaders = request.getHeaders();
        String token = tokenUtils.getTokenFromHeaders(requestHeaders);
        List<String> roles = cacheUtils.getValueFromCache(token);

        // Como el endpoint de usuarios requiere el rol de administrador,
        // mando el token y el rol por el header
        return webClient.get()
                .uri("/api/v1/usuarios")
                .header("Authorization", "Bearer " + token)
                .headers(headers -> headers.addAll("Role", roles))
                .retrieve()
                .bodyToFlux(UsuarioDTO.class)
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setId(usuario.getId());
                    usuarioDTO.setEmail(usuario.getEmail());
                    usuarioDTO.setNombre(usuario.getNombre());
                    usuarioDTO.setRoles(usuario.getRoles());
                    return usuarioDTO;
                });
    }

    public Mono<UsuarioDTO> findById(Long id, ServerHttpRequest request) {
        // Obtener el token y el rol del usuario del header
        HttpHeaders requestHeaders = request.getHeaders();
        String token = tokenUtils.getTokenFromHeaders(requestHeaders);
        List<String> roles = cacheUtils.getValueFromCache(token);

        // Incluir el ID en la URL de la solicitud GET
        return webClient.get()
                .uri("/api/v1/usuarios/{id}", id) // Reemplazar {id} con el valor del ID
                .header("Authorization", "Bearer " + token)
                .headers(headers -> headers.addAll("Role", roles))
                .retrieve()
                .bodyToMono(UsuarioDTO.class);
    }
}