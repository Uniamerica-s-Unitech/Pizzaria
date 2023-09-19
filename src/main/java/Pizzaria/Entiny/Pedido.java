package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter
@Entity
@Table(name = "pedido",schema = "public")
public class Pedido extends AbstractEntiny {
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clienteId;

    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;

    @ManyToMany(mappedBy = "pedidos")
    Set<Cliente> clientes;
}
