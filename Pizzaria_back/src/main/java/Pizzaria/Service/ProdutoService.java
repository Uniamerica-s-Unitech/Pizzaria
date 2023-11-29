package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.*;
import Pizzaria.Repositorye.PedidoRepository;
import Pizzaria.Repositorye.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public MensagemDTO cadastrarProduto(ProdutoDTO produtoDTO) {
        Produto produto = toProduto(produtoDTO);
        produtoRepository.save(produto);
        return new MensagemDTO("Produto cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = toProduto(produtoDTO);
        produtoRepository.save(produto);
        return new MensagemDTO("Produto atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) {
        Produto produtoBanco = produtoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto com ID "+id+" nao existe!"));

        List<Pedido> produtoPedidoAtivos = pedidoRepository.findPedidoAbertosPorProduto(produtoBanco);

        if (!produtoPedidoAtivos.isEmpty()){
            return new MensagemDTO("Não é possível excluir esse produto, pois existem pedidos ativos associados a ele.", HttpStatus.NOT_FOUND);
        } else {
            desativarProduto(produtoBanco);
        }
        return new MensagemDTO("Produto deletado com sucesso!", HttpStatus.CREATED);
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
        produtoDTO.setValor(produto.getValor());
        produtoDTO.setTemSabores(produto.getTemSabores());
        produtoDTO.setTamanho(produto.getTamanho());

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
        novoProduto.setValor(produtoDTO.getValor());
        novoProduto.setTemSabores(produtoDTO.getTemSabores());
        novoProduto.setTamanho(produtoDTO.getTamanho());

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