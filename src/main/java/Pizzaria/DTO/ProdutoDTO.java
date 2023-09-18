package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProdutoDTO extends AbstractEntinyDTO{
    private Long id;
    private String nome;
    private String tamanho;
}