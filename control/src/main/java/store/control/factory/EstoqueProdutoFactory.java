package store.control.factory;

import lombok.experimental.UtilityClass;
import store.control.entity.Coloracao;
import store.control.entity.EstoqueProduto;
import store.control.entity.Produto;

import java.text.DecimalFormat;

@UtilityClass
public class EstoqueProdutoFactory {

    public static EstoqueProduto criarEstoqueProduto (Produto produto){

        return EstoqueProduto.builder()
                .produtoId(produto)
                .valorTotal(formatarValor(calcularQuantidadeTotalProduto(produto) * produto.getValor()))
                .quantidadeTotal(calcularQuantidadeTotalProduto(produto))
                .build();
    }

    private static Integer calcularQuantidadeTotalProduto (Produto produto) {
        var quantidade = 0;

        var cores = produto.getCores();

        for (Coloracao cor: cores) {
            quantidade += cor.getTamanho().getP()
                    + cor.getTamanho().getM()
                    + cor.getTamanho().getG()
                    + cor.getTamanho().getGg();
        }
        return quantidade;
    }

    public Double formatarValor(Double valor) {
        var formater = new DecimalFormat("##00.00");
        var valor1 = formater.format(valor);
        return Double.valueOf(valor1.replace(",", "."));
    }
}
