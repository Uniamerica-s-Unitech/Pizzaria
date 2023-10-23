package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "categoria", schema = "public")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(length = 100, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "categoriaId",cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonIgnoreProperties("produtos")
    private List<Produto> produtos;
}