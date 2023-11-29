package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
public class PedidoDTO {
    private Long id;
    private Boolean ativo = true;
    private ClienteDTO clienteId;
    private EnderecoDTO enderecoId;
    private List<PedidoProdutoDTO> pedidoProdutoList;
    private String solicitacao;
    private String finalizacao;
    private Double valorTotal;
}