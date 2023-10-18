package Pizzaria.Repositorye;

import Pizzaria.Entiny.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
    @Query("FROM Endereco WHERE ativo = true")
    List<Endereco> findByAtivo();
}
