package store.control.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_produto")
    private String produtoNome;

    @Column(name = "valor_produto")
    private Double valor;

    @OneToOne(mappedBy = "produtoId")
    @JsonIgnore
    private EstoqueProduto estoqueProduto;

    @OneToMany(mappedBy = "produto")
    private List<Coloracao> cores;
}
