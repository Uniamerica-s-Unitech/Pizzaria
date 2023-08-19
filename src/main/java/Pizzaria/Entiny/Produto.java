package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto",schema = "public")
public class Produto {
    @Id
    @Getter@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String tamanho;
}
