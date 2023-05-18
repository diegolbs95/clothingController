package store.control.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.control.exception.ProdutoException;
import store.control.records.ColoracaoRc;
import store.control.records.ProdutoRc;
import store.control.records.ProdutoResponse;
import store.control.records.VenderProdutoRc;
import store.control.entity.Produto;
import store.control.repository.ProdutoRepository;
import store.control.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produto")
@Slf4j
public class ProdutoController {

    @Autowired
    private ProdutoService service;
    @Autowired
    private ProdutoRepository repository;

    @PostMapping("/adicionar-produto")
    public ResponseEntity<String> adicionarProduto (@RequestBody ProdutoRc produtoDto) {

        log.info("[PRODUTO-CONTROLLER]-Request adicionar produto!");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarProduto(produtoDto));
    }

    @GetMapping
    public ResponseEntity<Produto> buscarProdutoPorNome(@RequestParam String nomeProduto) {
        try {
            return ResponseEntity.ok(service.buscarProdutoPorNome(nomeProduto));
        } catch (ProdutoException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/listar-produtos")
    public ResponseEntity<List<Produto>> listarProdutos (){

        log.info("[PRODUTO-CONTROLLER]-Request listar produto!");
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping("/atualizar-produto")
    public ResponseEntity<String> atualizarProduto (@RequestParam String nomeProduto, @RequestBody ColoracaoRc coloracaoRc)
            throws ProdutoException {

        log.info("[PRODUTO-CONTROLLER]- Request atualizar produto!");
        var response = service.atualizarProduto (nomeProduto, coloracaoRc);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar-estoque-total-produto")
    public ResponseEntity<String> atualizarEstoqueTotalProduto (@RequestParam String nomeProduto, @RequestBody List<ColoracaoRc> coloracaoDto) {

        try {
            log.info("[PRODUTO-CONTROLLER]-Request atualizar estoque total do produto: " + nomeProduto);
            var response = service.atualizarEstoqueTotalProduto(nomeProduto, coloracaoDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO_NA_ATUALIZAÇÃO");
        }
    }

    @PutMapping("/realizar-venda-produto")
    public ResponseEntity<String> realizarVenda (@RequestParam String nomeProduto, @RequestBody VenderProdutoRc venderProdutoRequest) {

        try {
            log.info("[PRODUTO-CONTROLLER]- Request realização de venda referente ao produto: " + nomeProduto);
            var response = service.realizarVendaProduto (nomeProduto, venderProdutoRequest);
            return ResponseEntity.ok(response);
        } catch (ProdutoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PRODUTO_NÃO_ENCONTRADO!");
        }
    }
}