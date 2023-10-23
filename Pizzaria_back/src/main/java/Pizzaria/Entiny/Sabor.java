package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "sabor",schema = "public")
public class Sabor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "nome", length = 100)
    private String nome;
}