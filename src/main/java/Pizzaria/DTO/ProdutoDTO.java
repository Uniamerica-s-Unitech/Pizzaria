package Pizzaria.DTO;

import Pizzaria.Entiny.Sabor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ProdutoDTO extends AbstractEntinyDTO{
    private Long id;

    private String nome;

    private String tamanho;

    private SaborDTO saborId;

    @JsonIgnoreProperties("produtoId")
    private ProdutoDTO produtos;

}