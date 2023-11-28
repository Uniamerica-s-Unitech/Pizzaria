package Pizzaria.Repositorye;

import Pizzaria.Entiny.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Query("FROM Pedido WHERE finalizacao IS NULL")
    List<Pedido> findPedidosAbertos();
    @Query("FROM Pedido WHERE finalizacao IS NOT NULL")
    List<Pedido> findHistorico();

    @Query("FROM Pedido WHERE finalizacao IS NULL AND clienteId = :cliente")
    List<Pedido> findPedidoAbertosPorCliente(Cliente cliente);

    @Query("FROM Pedido WHERE finalizacao IS NULL AND :produto MEMBER OF pedidoProdutoList")
    List<Pedido> findPedidoAbertosPorProduto(Produto produto);
}