package Pizzaria.Repositorye;

import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Query("FROM Pedido WHERE ativo = true")
    List<Pedido> findByAtivo();
    @Query("FROM Pedido WHERE finalizado IS false AND clienteId = :cliente")
    List<Pedido> findPedidoAbertosPorCliente(Cliente cliente);
}
