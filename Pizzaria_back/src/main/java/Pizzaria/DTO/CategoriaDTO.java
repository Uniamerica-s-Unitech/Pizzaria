package Pizzaria.DTO;

import Pizzaria.Entiny.Produto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CategoriaDTO {
    private Long id;
    private Boolean ativo = true;
    private String nome;
    private List<ProdutoDTO> produtos;
}