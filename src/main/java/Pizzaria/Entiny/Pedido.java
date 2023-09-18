package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "pedido",schema = "public")
public class Pedido extends AbstractEntiny{
    private LocalDateTime dataHora;

    private String observacao;

    private Boolean status;
}
