package store.control.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.control.entity.Tamanho;
import store.control.repository.TamanhoRepository;

@Service
@Slf4j
public class TamanhoService {

    @Autowired
    private TamanhoRepository repository;

    public void adicionarTamanho (Tamanho tamanho) {

        repository.save(tamanho);
        log.info("Tamanho adicionado com sucesso!");
    }

    public void excluirTamanho (Long idTamanho) {
        repository.deleteById(idTamanho);
    }
}
