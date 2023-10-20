package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "pedido",schema = "public")
public class Pedido  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "cliente_id")
    private Cliente clienteId;

    @OneToMany(mappedBy = "produto_list",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Produto> produtos;

    @Column(name = "dataDeSolicitacao")
    private LocalDateTime soliciatacao;

    @Column(name = "dataDeFinalizacao")
    private LocalDateTime finalizacao;



}
