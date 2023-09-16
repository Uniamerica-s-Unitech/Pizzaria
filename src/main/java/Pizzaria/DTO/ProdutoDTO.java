package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String tamanho;
}