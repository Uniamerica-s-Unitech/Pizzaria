package Pizzaria.DTO;

import Pizzaria.Entiny.Pedido;

import Pizzaria.Entiny.Sabor;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class ProdutoDTO extends AbstractEntinyDTO{
    private Long id;
    private String nome;
    private String tamanho;
    private Pedido pedido;
    private Sabor sabores;
}