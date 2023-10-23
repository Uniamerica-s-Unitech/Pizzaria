package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "cliente", schema = "public")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(length = 100, nullable = false)
    private String nome;


    @OneToMany(mappedBy = "clienteId",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Endereco> enderecos;
}