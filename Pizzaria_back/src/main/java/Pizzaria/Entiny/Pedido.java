package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "pedido",schema = "public")
public class Pedido  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "cliente_id",nullable = false)
    private Cliente clienteId;

    @ManyToMany
    @JoinColumn(name = "produto_id",nullable = false)
    private List<Produto> produtos;

    @Column(name = "dataDeSolicitacao",nullable = false)
    private LocalDateTime solicitacao;

    @Column(name = "dataDeFinalizacao")
    private LocalDateTime finalizacao;

    @Column(name = "valorTotal")
    private Double valorTotal;
}