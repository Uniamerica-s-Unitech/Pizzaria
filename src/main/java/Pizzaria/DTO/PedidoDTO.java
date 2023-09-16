package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter@Setter
public class PedidoDTO {
    private Long id;

    private LocalDateTime dataHora;

    private String observacao;

    private Boolean status;
}