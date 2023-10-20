package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "sabor",schema = "public")
@Getter @Setter
public class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "nome", length = 100)
    private String nome;

    @ManyToOne()
    @JoinColumn(name = "produtos_id")
    private Produto produtoId;

}
