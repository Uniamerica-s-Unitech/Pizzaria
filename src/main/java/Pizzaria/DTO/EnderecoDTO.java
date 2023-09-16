package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EnderecoDTO {

    private Long id;

    private String rua;

    private Integer numero;

    private String referencia;

    private ClienteDTO cliente_DTO_id;
}