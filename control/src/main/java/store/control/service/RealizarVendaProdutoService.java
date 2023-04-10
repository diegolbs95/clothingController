package store.control.service;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import store.control.exception.ProdutoException;
import store.control.records.VenderProdutoRc;
import store.control.entity.Coloracao;
import store.control.entity.Produto;

import static store.control.AppContants.QUANTIDADE_MAIOR_QUE_ESTOQUE;

@UtilityClass
@Slf4j
public class RealizarVendaProdutoService {

    public static Produto venderProduto(Produto produto, VenderProdutoRc venderProdutoRequest) {

        try {
            for (Coloracao cor : produto.getCores()){
                if (cor.getNome().equals(venderProdutoRequest.cor().toUpperCase())){
                    switch (venderProdutoRequest.tamanho().toUpperCase()) {
                        case "P" -> cor.getTamanho().setP(validarDiminuirQuantidade(
                                cor.getTamanho().getP(), venderProdutoRequest.quantidade()));
                        case "M" -> cor.getTamanho().setM(validarDiminuirQuantidade(
                                cor.getTamanho().getM(), venderProdutoRequest.quantidade()));
                        case "G" -> cor.getTamanho().setM(validarDiminuirQuantidade(
                                cor.getTamanho().getG(), venderProdutoRequest.quantidade()));
                        case "GG" -> cor.getTamanho().setM(validarDiminuirQuantidade(
                                cor.getTamanho().getGg(), venderProdutoRequest.quantidade()));
                        default -> throw new IllegalArgumentException();
                    }
                }
            }
        } catch (IllegalArgumentException | ProdutoException e) {
            log.error(e.getMessage());
        }
        return produto;
    }

    private static Integer validarDiminuirQuantidade (Integer quantidadeTotal, Integer diminuirQuantidade) throws ProdutoException {

        if (quantidadeTotal >= diminuirQuantidade){
            return quantidadeTotal - diminuirQuantidade;
        }
        throw new ProdutoException(QUANTIDADE_MAIOR_QUE_ESTOQUE + quantidadeTotal);
    }
}
