package Pizzaria.Service;

import Pizzaria.Entiny.Sapor;
import Pizzaria.Repositorye.SaporRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaporService {

    @Autowired
    private SaporRepository repository;

    public List<Sapor> findAll(){
        return repository.findAll();
    }

    public void cadastrar(Sapor sapor){
        this.repository.save(sapor);
    }

    public Sapor editar(Long id, Sapor sapor){
        if (repository.existsById(id)){
            sapor.setId(id);
            return repository.save(sapor);
        }
        return null;
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
