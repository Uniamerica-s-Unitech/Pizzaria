package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto",schema = "public")
@Getter @Setter
public class Produto extends AbstractEntiny{
    private String nome;

    private String tamanho;
}
