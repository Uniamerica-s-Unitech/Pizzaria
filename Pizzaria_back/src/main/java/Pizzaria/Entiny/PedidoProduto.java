package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "pedido_produto",schema = "public")
public class PedidoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "pedido_id")
    private Pedido pedidoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "produto_id",nullable = false)
    private Produto produtoId;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto_sabor",
            joinColumns = @JoinColumn(name = "pedido_produto_id"),
            inverseJoinColumns = @JoinColumn(name = "sabor_id")
    )
    private List<Sabor> sabores;
}
