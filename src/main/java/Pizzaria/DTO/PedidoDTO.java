package Pizzaria.DTO;

import Pizzaria.Repositorye.PedidoRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter@Setter
public class PedidoDTO extends AbstractEntinyDTO{
    @JsonIgnoreProperties("clienteId")
    private ClienteDTO clienteId;

    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;

    /*@JsonIgnoreProperties("pedidos")
    private Set<ProdutoDTO> produtos;

    @JsonIgnoreProperties("pedidos")
    private Set<ClienteDTO> clientes;
*/
}