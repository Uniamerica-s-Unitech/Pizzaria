package Pizzaria.DTO;

import Pizzaria.Entiny.Cliente;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EnderecoDTO {

    private Long id;

    private Boolean ativo = true;

    private String bairro;

    private String rua;

    private Integer numero;

    private ClienteDTO cliente_id;

}