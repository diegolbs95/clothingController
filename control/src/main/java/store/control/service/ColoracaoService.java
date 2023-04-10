package store.control.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.control.entity.Coloracao;
import store.control.exception.ColoracaoException;
import store.control.repository.ColoracaoRepository;

@Service
@Slf4j
public class ColoracaoService {

    @Autowired
    private ColoracaoRepository repository;
    @Autowired
    private TamanhoService tamanhoService;

    public void adicionarCor (Coloracao coloracao){

        log.info("Adicionando nova Cor");
        coloracao.setNome(coloracao.getNome().toUpperCase());

        repository.save(coloracao);

        coloracao.getTamanho().setColoracao(coloracao);
        tamanhoService.adicionarTamanho(coloracao.getTamanho());
        log.info("Sucesso ao adicionar nova Cor!");
    }

    public Coloracao buscarCor (String nomeCor) throws ColoracaoException {
        try {
            log.info("Buscando cor no banco de dados");
            return repository.findByNome(nomeCor);
        } catch (Exception e){
            throw new ColoracaoException();
        }
    }
}
