package store.control.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.control.entity.Produto;
import store.control.records.ColoracaoRc;
import store.control.records.ProdutoRc;
import store.control.records.VenderProdutoRc;
import store.control.exception.ProdutoException;
import store.control.factory.AtualizarProdutoFactory;
import store.control.factory.ProdutoFactory;
import store.control.repository.ColoracaoRepository;
import store.control.repository.ProdutoRepository;
import store.control.repository.TamanhoRepository;
import java.util.List;

@Service
@Slf4j
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private ColoracaoService coloracaoService;
    @Autowired
    private ColoracaoRepository coloracaoRepository;
    @Autowired
    private TamanhoRepository tamanhoRepository;
    @Autowired
    private EstoqueProdutoService estoqueProdutoService;

    public String adicionarProduto(ProdutoRc produtoDto) {
        try {
            var produto = ProdutoFactory.criarProduto(produtoDto);

            log.info("Validando se produto ja existe na base de dados!");
            if (Boolean.TRUE.equals(validaProdutoExiste(produto.getProdutoNome()))){
                log.info("Produto ja existente no banco de dados!");
                return "PRODUTO_JA_EXISTE!";
            }

            repository.save(produto);

            produto.getCores().forEach(cor -> cor.setProduto(produto));
            produto.getCores().forEach(this.coloracaoService::adicionarCor);

            log.info("Acionando estoque para salvar valor total em estoque!");
            estoqueProdutoService.adicionarProdutoEstoque(produto);

            return "PRODUTO_ADICIONADO!";
        } catch (Exception e) {
            log.info("Erro ao adicionar produto!");
            return "ERRO_AO_ADICIONAR_PRODUTO!";
        }
    }

    public String atualizarProduto(String nomeProduto, ColoracaoRc coloracaoRc) throws ProdutoException {

        log.info("Buscando na base de dados produto com nome: " + nomeProduto);
        var produto = repository.findByProdutoNome(nomeProduto).orElseThrow(ProdutoException::new);

        var produtoAtualizado = AtualizarProdutoFactory.atualizarProduto(produto, coloracaoRc);
        repository.save(produtoAtualizado);
        log.info("Atualizar estoque produto!");
        estoqueProdutoService.atualizarEstoqueProduto(produtoAtualizado);
        return "PRODUTO_ATUALIZADO!";
    }

    public String atualizarEstoqueTotalProduto(String nomeProduto, List<ColoracaoRc> coloracaoDto) {
        try {

            log.info("Buscando produto na base de dados!");
            var produto = repository.findByProdutoNome(nomeProduto).orElseThrow(ProdutoException::new);

            var produtoAtualizado = AtualizarProdutoFactory.atualizarEstoqueTotalProduto(produto, coloracaoDto);
            repository.save(produtoAtualizado);
            estoqueProdutoService.atualizarEstoqueProduto(produtoAtualizado);
            log.info(String.format("Atualização de tamanhos do produto: %s, atualizado com sucesso!", nomeProduto));

            return "SUCESSO!";
        } catch (ProdutoException e) {
            log.error("[ERRO]-Erro ao atualizar produto causa: " + e.getMessage());
            return "PRODUTO_NAO_ENCONTRATO";
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERRO]-Erro ao atualizar produto causa: " + e.getMessage());
        }
    }

    public String realizarVendaProduto(String nomeProduto, VenderProdutoRc venderProdutoRequest) throws ProdutoException {

        try {

            var produto = repository.findByProdutoNome(nomeProduto).orElseThrow(Exception::new);
            log.info("[API-VENDA-PRODUTO]- Produto encontrado na base de dados produto: " + nomeProduto);
            var produtoAtualizado = RealizarVendaProdutoService.venderProduto(produto, venderProdutoRequest);
            repository.save(produtoAtualizado);
            estoqueProdutoService.reduzirProdutoEstoque(produto, produto.getValor(), venderProdutoRequest.quantidade());
            return "VENDA_REALIZADA_COM_SUCESSO!";

        } catch (Exception e){
            log.error("[ERRO]-Erro realização de venda produto causa: " + e.getMessage());
            throw new ProdutoException("[ERRO]-Produto não encontrato na base de dados!");

        }
    }

    private Boolean validaProdutoExiste (String nomeProduto) {
        var listProdutos = repository.findAll();

        for (Produto prod : listProdutos) {
            if (prod.getProdutoNome().equals(nomeProduto)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
