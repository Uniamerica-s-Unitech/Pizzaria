package Pizzaria.Service;

import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaborService {

    @Autowired
    private SaborRepository repository;

    public List<Sabor> findAll(){
        return repository.findAll();
    }

    public void cadastrar(Sabor sabor){
        this.repository.save(sabor);
    }

   /* public Sabor editar(Long id, Sabor sabor){
        if (repository.existsById(id)){
            sabor.setId(id);
            return repository.save(sabor);
        }
        return null;
    }*/

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
