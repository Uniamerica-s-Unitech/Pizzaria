package Pizzaria.Repositorye;

import Pizzaria.Entiny.ClientePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientePedidoRepository extends JpaRepository<ClientePedido,Long> {


}
