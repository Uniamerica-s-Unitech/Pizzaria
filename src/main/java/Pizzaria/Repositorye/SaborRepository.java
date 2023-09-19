package Pizzaria.Repositorye;

import Pizzaria.Entiny.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaborRepository extends JpaRepository<Sabor,Long> {
    @Query("FROM Sabor WHERE ativo = true")
    List<Sabor> findByAtivo();
}
