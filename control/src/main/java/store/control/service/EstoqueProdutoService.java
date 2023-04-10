package store.control.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.control.entity.Coloracao;
import store.control.entity.Produto;
import store.control.exception.ProdutoException;
import store.control.factory.EstoqueProdutoFactory;
import store.control.repository.EstoqueProdutoRepository;
import store.control.repository.ProdutoRepository;

@Service
@Slf4j
public class EstoqueProdutoService {

    @Autowired
    private EstoqueProdutoRepository repository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public void adicionarProdutoEstoque (Produto produto) {

        log.info("Salvando valor total em estoque do produto: " + produto.getProdutoNome());
        repository.save(EstoqueProdutoFactory.criarEstoqueProduto(produto));
    }

    public void reduzirProdutoEstoque (Produto produto, Double valor, Integer quantidade) {
         log.info("Buscando produto na base de dados!");
         var produtoEstoque = repository.findByProdutoId(produto);
         produtoEstoque.setValorTotal(EstoqueProdutoFactory
                 .formatarValor(produtoEstoque.getValorTotal() - (valor * quantidade)));

         produtoEstoque.setQuantidadeTotal(produtoEstoque.getQuantidadeTotal() - quantidade);

         repository.save(produtoEstoque);
    }

    public void atualizarEstoqueProduto (Produto prod) throws ProdutoException {
        var quantidade = 0;
        log.info("Buscando produto na base de dados!");
        var prodEstoque = repository.findByProdutoId(prod);

        var produto = produtoRepository.findByProdutoNome(prod.getProdutoNome())
                .orElseThrow(ProdutoException::new);

        for (Coloracao coloracao: produto.getCores()) {
            quantidade += coloracao.getTamanho().getP()
                    + coloracao.getTamanho().getM()
                    + coloracao.getTamanho().getG()
                    + coloracao.getTamanho().getGg();
        }

        prodEstoque.setValorTotal(EstoqueProdutoFactory
                .formatarValor(produto.getValor() * quantidade));
        prodEstoque.setQuantidadeTotal(quantidade);
        repository.save(prodEstoque);
    }
}
