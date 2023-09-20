package Pizzaria.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class ClienteDTO extends AbstractEntinyDTO{
    private String nome;

    @JsonIgnoreProperties({"clienteId", "clientes"})
    private Set<PedidoDTO> pedidos;

}