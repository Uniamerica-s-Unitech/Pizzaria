package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EnderecoDTO extends AbstractEntinyDTO{
    private String rua;

    private Integer numero;

    private String referencia;

    private Cliente clienteId;
}