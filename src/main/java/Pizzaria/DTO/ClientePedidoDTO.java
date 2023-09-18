package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class ClientePedidoDTO extends AbstractEntinyDTO{
    private ClienteDTO clienteDTO;

    private PedidoDTO pedidoDTO;
}