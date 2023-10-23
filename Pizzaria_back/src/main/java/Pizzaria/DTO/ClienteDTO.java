package Pizzaria.DTO;

import Pizzaria.Entiny.Endereco;
import lombok.*;
import java.util.List;

@Getter@Setter
public class ClienteDTO {
    private Long id;
    private Boolean ativo = true;
    private String nome;
    private List<EnderecoDTO> enderecos;
}