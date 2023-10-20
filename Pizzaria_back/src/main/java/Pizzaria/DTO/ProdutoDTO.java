package Pizzaria.DTO;

import Pizzaria.Entiny.Categoria;
import Pizzaria.Entiny.Pedido;

import Pizzaria.Entiny.Sabor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter@Setter
public class ProdutoDTO{
    private Long id;
    private Boolean ativo = true;
    private List<Sabor> sabores;
    private Categoria categoriaId;


}