package Pizzaria.Service;

import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto findById(Long id){
        return produtoRepository.findById(id).orElse(null);
    }

    public List<ProdutoDTO> listar(){
        List<Produto> produtos = produtoRepository.findByAtivo();
        return produtos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO cadastrar(ProdutoDTO produtoDTO){
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDTO,produto);
        produto = produtoRepository.save(produto);
        return convertToDTO(produto);
    }

    public ProdutoDTO editar(Long id,ProdutoDTO produtoDTO){
        if (produtoRepository.existsById(id)){
            Produto produto = produtoRepository.findById(id).orElse(null);
            if (produto != null){
                BeanUtils.copyProperties(produtoDTO,produto,"id");
                produto = produtoRepository.save(produto);
                return convertToDTO(produto);
            }
        }else {
            throw new IllegalArgumentException("Produto não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void dezAtivar(Long id, Produto produto) {
        Produto produtoBanco = produtoRepository.findById(id).orElse(null);
        if (produtoBanco != null){
            produto.setAtivo(false);
            produtoRepository.save(produto);
        }else {
            throw new IllegalArgumentException("Produto não encontrado com o ID: " + id);
        }
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        BeanUtils.copyProperties(produto, produtoDTO);
        return produtoDTO;
    }
}
