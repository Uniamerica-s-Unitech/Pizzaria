package Pizzaria.DTO;

import Pizzaria.Entiny.Categoria;

import Pizzaria.Entiny.Sabor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter@Setter
public class ProdutoDTO{
    private Long id;
    private String nome;

    private Boolean ativo = true;
    private List<SaborDTO> sabores;

    private CategoriaDTO categoriaId;


}