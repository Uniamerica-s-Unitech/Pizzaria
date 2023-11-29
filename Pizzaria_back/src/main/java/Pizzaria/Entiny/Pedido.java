package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "endereco_cliente_id",nullable = false)
    private Endereco enderecoId;

    @OneToMany(mappedBy = "pedidoId",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pedidoId")
    private List<PedidoProduto> pedidoProdutoList;

    @Column(name = "dataDeSolicitacao",nullable = false)
    private String solicitacao;

    @Column(name = "dataDeFinalizacao")
    private String finalizacao;

    @Column(name = "valorTotal")
    private Double valorTotal;
}