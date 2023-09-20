package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class ClientePedidoDTO extends AbstractEntinyDTO{
    private ClienteDTO cliente;

    private PedidoDTO pedido;
}