package Pizzaria.Service;

import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> findAll(){
        return repository.findAll();
    }

    public void cadastrar(Produto produto){
        this.repository.save(produto);
    }

    public Produto editar(Long id,Produto produto){
        if (repository.existsById(id)){
            produto.setId(id);
            return repository.save(produto);
        }
        return null;
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
