package Pizzaria.Repositorye;

import Pizzaria.Entiny.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository  extends JpaRepository<Categoria, Long> {
   @Query("SELECT c FROM Categoria c LEFT JOIN FETCH c.produtos p WHERE c.ativo = true AND (p.ativo = true or p = null)")
    List<Categoria> findCategoriaByAtivo();
}