package Pizzaria.Repositorye;

import Pizzaria.Entiny.Categoria;
import Pizzaria.Entiny.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository  extends JpaRepository<Categoria, Long> {

    @Query("FROM Categoria WHERE ativo = true")
    List<Categoria> findCategoriaByAtivo();


}
