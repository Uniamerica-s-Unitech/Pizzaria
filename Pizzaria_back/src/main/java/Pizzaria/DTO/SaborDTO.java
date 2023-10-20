package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SaborDTO {
    private Long id;
    private Boolean ativo = true;
    private String nome;

}