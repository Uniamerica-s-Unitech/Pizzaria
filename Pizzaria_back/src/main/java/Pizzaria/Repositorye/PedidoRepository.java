package Pizzaria.Repositorye;

import Pizzaria.Entiny.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Query("FROM Pedido WHERE ativo = true")
    List<Pedido> findByAtivo();
}
