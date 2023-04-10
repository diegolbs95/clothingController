package store.control.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_coloracao")
public class Coloracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Cor")
    private String nome;

    @OneToOne(mappedBy = "coloracao", cascade = CascadeType.PERSIST)
    private Tamanho tamanho;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

}
