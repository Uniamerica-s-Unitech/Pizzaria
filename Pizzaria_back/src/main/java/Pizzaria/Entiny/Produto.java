package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "produto",schema = "public")
@Getter @Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(length = 100, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "produtoId",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Sabor> sabores;

    @ManyToOne()
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaId;


}
