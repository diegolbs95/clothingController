package store.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.control.entity.Tamanho;

@Repository
public interface TamanhoRepository extends JpaRepository<Tamanho,Long> {

}
