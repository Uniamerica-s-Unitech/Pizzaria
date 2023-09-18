package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter@Setter
public class PedidoDTO extends AbstractEntinyDTO{
    private LocalDateTime dataHora;

    private String observacao;

    private Boolean status;
}