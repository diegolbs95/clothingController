package store.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.control.entity.EstoqueProduto;
import store.control.entity.Produto;

@Repository
public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto,Long> {

    EstoqueProduto findByProdutoId (Produto produtoId);

}
