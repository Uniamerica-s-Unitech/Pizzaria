package Pizzaria.Service;

import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository,
                          ModelMapper modelMapper){
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }

    public ProdutoDTO findById(Long id) {
        try{
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Produto nao existe" + id));
            return toClienteDTO(produto);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o produto."+ex.getMessage(), ex);
        }
    }

    public List<ProdutoDTO> listar(){
        return produtoRepository.findByAtivo().stream().map(this::toClienteDTO).toList();
    }

    public Produto cadastrar(ProdutoDTO produtoDTO){
        Produto produtoNovo = modelMapper.map(produtoDTO,Produto.class);
        return produtoRepository.save(produtoNovo);
    }

    public Produto editar(Long id, ProdutoDTO produtoDTO) {
        if (produtoRepository.existsById(id)) {
            Produto produtoBanco = produtoRepository.findById(id).orElseThrow(()
                    -> new IllegalArgumentException("Produto não encontrado com o ID fornecido: " + id));

            produtoBanco.setNome(produtoDTO.getNome());

            return produtoRepository.save(produtoBanco);
        } else {
            throw new IllegalArgumentException("Produto não encontrado com o ID fornecido: " + id);
        }
    }


    /*public void delete(Long id) {
        Produto produtoBanco = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID "+id+" nao existe"));

        if (produtoBanco.getPedido().isEmpty()){
            desativarProduto(produtoBanco);
        }else {
            throw new IllegalArgumentException("Produto tem Pedido em andamento nao pode ser deletado");
        }
    }

    private void desativarProduto(Produto produto) {
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }*/

    public ProdutoDTO toClienteDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

}
