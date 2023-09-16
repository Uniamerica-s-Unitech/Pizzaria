package Pizzaria.Entiny;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD

=======
@Getter @Setter
>>>>>>> e774f7f (atualizado)
@Entity
@Table(name = "cliente", schema = "public")
public class Cliente {
    @Id
<<<<<<< HEAD
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
=======
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

>>>>>>> e774f7f (atualizado)
    private String nome;
}
