package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "cliente", schema = "public")
public class Cliente extends AbstractEntiny{
    private String nome;
}
