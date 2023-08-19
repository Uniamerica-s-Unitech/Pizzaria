package Pizzaria.Service;

import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> findAll(){
        return repository.findAll();
    }

    public void cadastrar(Pedido pedido){
        this.repository.save(pedido);
    }

    public Pedido editar(Long id,Pedido pedido){
        if (repository.existsById(id)){
            pedido.setId(id);
            return repository.save(pedido);
        }
        return null;
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
