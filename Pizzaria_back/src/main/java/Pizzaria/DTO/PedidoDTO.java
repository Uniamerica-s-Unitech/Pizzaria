package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Produto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class PedidoDTO {
    private Long id;
    private Boolean ativo = true;
    private ClienteDTO clienteId;
    private List<ProdutoDTO> produtos;
    private LocalDateTime solicitacao;
    private LocalDateTime finalizacao;
    private Double valorTotal;
}