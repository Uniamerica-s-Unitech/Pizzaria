package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "cliente", schema = "public")
@Getter @Setter
public class Cliente extends AbstractEntiny{
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "clientepedido",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "pedido_id"))
            @JsonIgnoreProperties("clienteId")
    Set<Pedido> pedidos;
}
