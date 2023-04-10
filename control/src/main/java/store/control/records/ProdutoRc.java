package store.control.records;

import java.util.List;

public record ProdutoRc(String produtoNome, List<ColoracaoRc> produtoCores, Double valor) {}
