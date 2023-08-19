package Pizzaria.Service;

import Pizzaria.Entiny.ClientePedido;
import Pizzaria.Repositorye.ClientePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientePedidoService {

    @Autowired
    private ClientePedidoRepository repository;

    public List<ClientePedido> findAll(){
        return repository.findAll();
    }
}
