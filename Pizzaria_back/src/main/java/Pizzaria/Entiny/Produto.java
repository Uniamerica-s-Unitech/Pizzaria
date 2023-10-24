package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@Table(name = "produto",schema = "public")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(length = 100, nullable = false)
    private String nome;

    @OneToMany
    @JoinColumn(name = "sabor_id")
    private List<Sabor> sabores;

    @ManyToOne
    @JoinColumn(name = "categoria_id",nullable = false)
    private Categoria categoriaId;
}