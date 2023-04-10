package store.control.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_tamanho")
public class Tamanho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "P")
    private Integer p;

    @Column(name = "M")
    private Integer m;

    @Column(name = "G")
    private Integer g;

    @Column(name = "GG")
    private Integer gg;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "coloracao_id")
    private Coloracao coloracao;
}
