package Pizzaria.Entiny;

import jakarta.persistence.*;

@Entity
@Table(name = "clientepedido",schema = "public")
public class ClientePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
