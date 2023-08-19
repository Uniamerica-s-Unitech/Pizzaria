package Pizzaria.Service;

import Pizzaria.Entiny.Cliente;
import Pizzaria.Repositorye.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<Cliente> findAll(){
        return repository.findAll();
    }

    public Cliente cadastrar(Cliente cliente){
    return repository.save(cliente);
    }

    public Cliente editar(Long id,Cliente cliente){
        if(repository.existsById(id)){
            cliente.setId(id);
            return repository.save(cliente);
        }
        return null;
    }

    public void deletar(Long id) {
        Cliente cliente = repository.findById(id).orElse(null);
        if (cliente != null) {
            repository.delete(cliente);
        }
    }
}
