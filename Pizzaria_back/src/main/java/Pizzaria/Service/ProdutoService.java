package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.*;
import Pizzaria.Repositorye.PedidoRepository;
import Pizzaria.Repositorye.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public ProdutoDTO findProdutoById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("produto  não encontrado!"));
        return produtoToDTO(produto);
    }

    public List<ProdutoDTO> listar() {
        return produtoRepository.findProdutoByAtivo().stream().map(this::produtoToDTO).toList();
    }
    public String cadastrarProduto(ProdutoDTO produtoDTO) {
        Produto produto = toProduto(produtoDTO);

        Assert.notNull(produto.getNome(),"Nome inválido!");
        produtoRepository.save(produto);
        return "Produto cadastrado com sucesso!";
    }

    public String editarProduto(Long id, ProdutoDTO produtoDTO) {
        if (produtoRepository.existsById(id)) {
            Produto produto = toProduto(produtoDTO);

            Assert.notNull(produto.getNome(), "Nome inválido!");

            produtoRepository.save(produto);
            return "Produto atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("Produto não encontrado com o ID fornecido: " + id);
        }
    }

    public void deletar(Long id) {
        Produto produtoBanco = produtoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto com ID "+id+" nao existe!"));

        List<Pedido> produtoPedidoAtivos = pedidoRepository.findPedidoAbertosPorProduto(produtoBanco);

        if (!produtoPedidoAtivos.isEmpty()){
            throw new IllegalArgumentException("Não é possível excluir esse produto tem pedido ativo.");
        } else {
            desativarProduto(produtoBanco);
        }
    }

    private void desativarProduto(Produto produto) {
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
    public ProdutoDTO produtoToDTO(Produto produto){
        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setId(produto.getId());
        produtoDTO.setAtivo(produto.getAtivo());
        produtoDTO.setNome(produto.getNome());

        List<SaborDTO> listaEnd = new ArrayList<>();

        if(produto.getSabores() != null)
            for(int i=0; i<produto.getSabores().size(); i++){
                listaEnd.add(toSaborDTO(produto.getSabores().get(i)));
            }
        produtoDTO.setSabores(listaEnd);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(produto.getCategoriaId().getId());
        categoriaDTO.setAtivo(produto.getCategoriaId().getAtivo());
        categoriaDTO.setNome(produto.getCategoriaId().getNome());

        produtoDTO.setCategoriaId(categoriaDTO);

        return produtoDTO;
    }
    public Produto toProduto(ProdutoDTO produtoDTO){
        Produto novoProduto = new Produto();

        novoProduto.setId(produtoDTO.getId());
        novoProduto.setAtivo(produtoDTO.getAtivo());
        novoProduto.setNome(produtoDTO.getNome());

        List<Sabor> listaEnd = new ArrayList<>();
        if(produtoDTO.getSabores() != null)
            for(int i=0; i<produtoDTO.getSabores().size(); i++){
                listaEnd.add(toSabor(novoProduto,produtoDTO.getSabores().get(i)));
            }

        novoProduto.setSabores(listaEnd);

        Categoria categoria = new Categoria();
        categoria.setId(produtoDTO.getCategoriaId().getId());

        novoProduto.setCategoriaId(categoria);

        return novoProduto;
    }

    public Sabor toSabor(Produto novoProduto, SaborDTO saborDTO){
        Sabor novoSabor = new Sabor();

        novoSabor.setId(saborDTO.getId());
        novoSabor.setAtivo(saborDTO.getAtivo());
        novoSabor.setNome(saborDTO.getNome());

        return novoSabor;
    }
    public SaborDTO toSaborDTO(Sabor sabor){
        SaborDTO novoSabor = new SaborDTO();

        novoSabor.setId(sabor.getId());
        novoSabor.setAtivo(sabor.getAtivo());
        novoSabor.setNome(sabor.getNome());
        return novoSabor;
    }

}
