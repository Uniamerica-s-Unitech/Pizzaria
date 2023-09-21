package Pizzaria.DTO;

import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.PedidoRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter@Setter
public class PedidoDTO extends AbstractEntinyDTO{
    @JsonIgnoreProperties("clienteId")
    private ClienteDTO clienteId;

    private List<Produto> produtos;

    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;
}