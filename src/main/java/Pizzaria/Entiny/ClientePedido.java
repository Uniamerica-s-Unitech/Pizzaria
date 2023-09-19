package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientepedido",schema = "public")
@Getter@Setter
public class ClientePedido extends AbstractEntiny{
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
