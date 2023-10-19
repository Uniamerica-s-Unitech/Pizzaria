package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EnderecoDTO {

    private Long id;

    private Boolean ativo = true;

    private String bairro;

    private String rua;

    private int numero;

    private ClienteDTO clienteId;

}