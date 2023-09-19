package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "endereco", schema = "public")
public class Endereco extends AbstractEntiny{
    private String rua;

    private Integer numero;

    private String referencia;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente clienteId;
}
