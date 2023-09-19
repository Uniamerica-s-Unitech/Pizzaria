package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class ClientePedidoDTO extends AbstractEntinyDTO{
    private Cliente cliente;

    private Pedido pedido;
}