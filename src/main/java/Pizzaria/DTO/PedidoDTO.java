package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter@Setter
public class PedidoDTO extends AbstractEntinyDTO{
    @JsonIgnoreProperties("clienteId")
    private Cliente clienteId;

    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;

    @JsonIgnoreProperties("pedidos")
    private Set<ClienteDTO> clientes;

}