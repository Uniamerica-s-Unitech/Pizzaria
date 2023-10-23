package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "endereco", schema = "public")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "rua", length = 100)
    private String rua;

    @Column(name = "numero", length = 100)
    private int numero;

    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Cliente clienteId;
}