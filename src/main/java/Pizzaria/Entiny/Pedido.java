package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "pedido",schema = "public")
public class Pedido extends AbstractEntiny {
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("pedidos")
    private Cliente clienteId;

    @OneToMany(mappedBy = "pedido",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();


    private LocalDateTime dataHora;

    private String observacao;

    private Integer status;

}
