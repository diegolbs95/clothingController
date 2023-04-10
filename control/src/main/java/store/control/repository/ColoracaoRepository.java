package store.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.control.entity.Coloracao;

@Repository
public interface ColoracaoRepository extends JpaRepository<Coloracao,Long> {

    Coloracao findByNome(String nomeCor);
}
