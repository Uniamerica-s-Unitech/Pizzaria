package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "produto",schema = "public")
@Getter @Setter
public class Produto extends AbstractEntiny{
    private String nome;

    private String tamanho;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @OneToMany(mappedBy = "produto")
    private List<Sabor> sabores = new ArrayList<>();

}
