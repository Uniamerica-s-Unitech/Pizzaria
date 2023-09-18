package Pizzaria.Entiny;

import jakarta.persistence.*;

@Entity
@Table(name = "clientepedido",schema = "public")
public class ClientePedido extends AbstractEntiny{
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
