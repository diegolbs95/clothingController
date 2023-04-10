package store.control.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.control.entity.Coloracao;
import store.control.exception.ColoracaoException;
import store.control.service.ColoracaoService;

@RestController
@RequestMapping("/coloracao")
@Slf4j
public class ColoracaoController {

    @Autowired
    private ColoracaoService service;

    @GetMapping("/buscar-cor")
    public ResponseEntity<Coloracao> buscarCor (@RequestHeader String nomeCor) {

        try {
            log.info("Acionando API buscar coloracao!");
            return ResponseEntity.ok(service.buscarCor(nomeCor));
        } catch (ColoracaoException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
