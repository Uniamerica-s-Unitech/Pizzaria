package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class ClientePedidoDTO {

    private Long id;

    private ClienteDTO clienteDTO;

    private PedidoDTO pedidoDTO;
}