package Pizzaria.Repositorye;

import Pizzaria.Entiny.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
    @Query("FROM Endereco WHERE ativo = true")
    List<Endereco> findByAtivo();
}
