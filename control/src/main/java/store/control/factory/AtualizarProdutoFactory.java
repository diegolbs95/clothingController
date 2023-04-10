package store.control.factory;

import lombok.experimental.UtilityClass;
import store.control.records.ColoracaoRc;
import store.control.entity.Coloracao;
import store.control.entity.Produto;

import java.util.List;

@UtilityClass
public class AtualizarProdutoFactory {

    public static Produto atualizarEstoqueTotalProduto(Produto produto, List<ColoracaoRc> cor) {

        for (ColoracaoRc corDto: cor) {
            for (Coloracao coloracaoProduto: produto.getCores()) {
                if (coloracaoProduto.getNome().equals(corDto.coloracaoNome().toUpperCase())){
                    coloracaoProduto.getTamanho().setP(corDto.coloracaoTamanho().pTamanho());
                    coloracaoProduto.getTamanho().setM(corDto.coloracaoTamanho().mTamanho());
                    coloracaoProduto.getTamanho().setG(corDto.coloracaoTamanho().gTamanho());
                    coloracaoProduto.getTamanho().setGg(corDto.coloracaoTamanho().ggTamanho());
                }
            }
        }
        return produto;
    }

    public static Produto atualizarProduto (Produto produto, ColoracaoRc cor) {

        for (Coloracao cors:produto.getCores()) {
            if (cor.coloracaoNome().toUpperCase().equals(cors.getNome())){
                validarTamanhos(cors, cor);
            }
        }
        return produto;
    }

    private static void validarTamanhos (Coloracao cor, ColoracaoRc corRc) {
        if (corRc.coloracaoTamanho().pTamanho() != null){
            cor.getTamanho().setP(cor.getTamanho().getP() + corRc.coloracaoTamanho().pTamanho());
        }
        if (corRc.coloracaoTamanho().mTamanho() != null){
            cor.getTamanho().setM(cor.getTamanho().getM() + corRc.coloracaoTamanho().mTamanho());
        }
        if (corRc.coloracaoTamanho().gTamanho() != null){
            cor.getTamanho().setG(cor.getTamanho().getG() + corRc.coloracaoTamanho().gTamanho());
        }
        if (corRc.coloracaoTamanho().ggTamanho() != null){
            cor.getTamanho().setGg(cor.getTamanho().getGg() + corRc.coloracaoTamanho().ggTamanho());
        }
    }
}
