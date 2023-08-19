package Pizzaria.Service;

import Pizzaria.Entiny.Endereco;
import Pizzaria.Repositorye.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public List<Endereco> findAll(){
        return repository.findAll();
    }

    public void cadastrar(Endereco endereco){
         this.repository.save(endereco);
    }

    public Endereco editar(Long id,Endereco endereco){
        if (repository.existsById(id)){
            endereco.setId(id);
            return repository.save(endereco);
        }
        return null;
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
