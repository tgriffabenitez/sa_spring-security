package com.sistemasactivos.msbff.config;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    /**
     * Crea un WebClient para comunicarse con el servicio de inicio de sesión.
     *
     * @return El WebClient configurado para el servicio de inicio de sesión.
     */
    @Bean
    @Qualifier("signInWebClient")
    public WebClient signInWebClient() {
        String baseUrl = "https://localhost:443";
        return createWebClient(baseUrl);
    }

    /**
     * Crea un WebClient para comunicarse con el servicio de empleados.
     *
     * @return El WebClient configurado para el servicio de empleados.
     */
    @Bean
    @Qualifier("empleadosWebClient")
    public WebClient msempleadosWebClient() {
        String baseUrl = "https://localhost:442/api/v1";
        return createWebClient(baseUrl);
    }

    /**
     * Crea y configura un WebClient con la configuración proporcionada.
     *
     * @param baseUrl La URL base del servicio.
     * @return El WebClient configurado.
     */
    private WebClient createWebClient(String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

        WebClient.Builder webClientBuilder = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient));

        return webClientBuilder.build();
    }
}

