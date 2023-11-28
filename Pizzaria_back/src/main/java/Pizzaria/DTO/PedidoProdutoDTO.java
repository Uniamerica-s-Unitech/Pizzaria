package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PedidoProdutoDTO {
    private Long id;
    private Boolean ativo = true;
    private PedidoDTO pedidoId;
    private ProdutoDTO produtoId;
    private List<SaborDTO> sabores;
}
