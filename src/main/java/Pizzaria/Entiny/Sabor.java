package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "sabor",schema = "public")
@Getter @Setter
public class Sabor extends AbstractEntiny{
    private String nome;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
