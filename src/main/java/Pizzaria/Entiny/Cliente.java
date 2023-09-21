package Pizzaria.Entiny;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cliente", schema = "public")
@Getter @Setter
public class Cliente extends AbstractEntiny{
    private String nome;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "clienteId")
    @JsonIgnore
    private List<Pedido> pedidos;


}
