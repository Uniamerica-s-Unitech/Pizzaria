package Pizzaria.DTO;

import Pizzaria.Entiny.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ClienteDTO extends AbstractEntinyDTO{
    private String nome;

    private List<Endereco> enderecos;

}