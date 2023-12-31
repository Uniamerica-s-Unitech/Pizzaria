package Pizzaria.Repositorye;

import Pizzaria.Entiny.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    @Query("FROM Cliente WHERE ativo = true")
    List<Cliente> findClienteByAtivo();
}