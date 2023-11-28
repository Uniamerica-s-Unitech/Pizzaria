package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProdutoDTO{
    private Long id;
    private String nome;
    private Boolean ativo = true;
    private CategoriaDTO categoriaId;
    private Double valor;
    private Boolean temSabores = false;
    private String tamanho;
}