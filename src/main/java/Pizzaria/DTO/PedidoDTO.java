package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter@Setter
public class PedidoDTO extends AbstractEntinyDTO{
    private Cliente clienteId;

    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;
}