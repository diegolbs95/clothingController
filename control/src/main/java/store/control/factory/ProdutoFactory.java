package store.control.factory;

import lombok.experimental.UtilityClass;
import store.control.records.ColoracaoRc;
import store.control.records.ProdutoRc;
import store.control.records.TamanhoRc;
import store.control.entity.Coloracao;
import store.control.entity.Produto;
import store.control.entity.Tamanho;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProdutoFactory {

    public static Produto criarProduto (ProdutoRc produtoDto) {
        return Produto.builder()
                .valor(produtoDto.valor())
                .produtoNome(produtoDto.produtoNome())
                .cores(getListCores(produtoDto.produtoCores()))
                .build();
    }

    private static List<Coloracao> getListCores(List<ColoracaoRc> produtoCores) {
        var list = new ArrayList<Coloracao>();

        produtoCores.forEach(corDto -> {
            var cor = Coloracao.builder()
                    .nome(corDto.coloracaoNome())
                    .tamanho(getTamanho(corDto.coloracaoTamanho()))
                    .build();

            list.add(cor);
        });
        return list;
    }

    private static Tamanho getTamanho(TamanhoRc coloracaoTamanho) {
        return Tamanho.builder()
                .p(coloracaoTamanho.pTamanho())
                .m(coloracaoTamanho.mTamanho())
                .g(coloracaoTamanho.gTamanho())
                .gg(coloracaoTamanho.ggTamanho())
                .build();
    }
}
