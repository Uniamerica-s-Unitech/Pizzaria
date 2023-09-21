package Pizzaria.DTO;

import Pizzaria.Entiny.Produto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class PedidoDTO extends AbstractEntinyDTO{
    @JsonIgnoreProperties("pedidos")
    private ClienteDTO clienteId;

    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;

    private List<Produto> produtos = new ArrayList<>();
}