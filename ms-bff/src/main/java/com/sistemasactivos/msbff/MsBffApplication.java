package com.sistemasactivos.msbff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class MsBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBffApplication.class, args);
    }

}
