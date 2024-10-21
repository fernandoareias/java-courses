package com.fernando.cambio.service.controllers;

import com.fernando.cambio.service.models.Cambio;
import com.fernando.cambio.service.repositories.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cambios")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository cambioRepository;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {

        var port = environment.getProperty("local.server.port");

        var cambio = Cambio
                .builder()
                .Id(1L)
                .from(from)
                .to(to)
                .environment(String.format("PORT %s", port))
                .convertedValue(BigDecimal.ONE)
                .conversionFactor(BigDecimal.ONE)
                .build();

        cambioRepository.save(cambio);

        return cambio;
    }

}
