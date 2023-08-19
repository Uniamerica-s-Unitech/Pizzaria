package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido",schema = "public")
public class Pedido {
    @Id
    @Getter@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    private LocalDateTime dataHora;
    @Getter @Setter
    private String observacao;
    @Getter @Setter
    private Integer status;
}
