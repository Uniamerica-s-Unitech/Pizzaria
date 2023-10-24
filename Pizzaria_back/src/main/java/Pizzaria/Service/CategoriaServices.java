package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.*;
import Pizzaria.Repositorye.CategoriaRepository;
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
public class CategoriaServices {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public CategoriaDTO findCategoriaById(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada!"));
        return categoriaToDTO(categoria);
    }

    public List<CategoriaDTO> listar(){
        return categoriaRepository.findCategoriaByAtivo().stream().map(this::categoriaToDTO).toList();
    }

    public MensagemDTO cadastrarCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria = toCategoria(categoriaDTO);
        categoriaRepository.save(categoria);
        return new MensagemDTO("Categoria cadastrada com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarCategoria(Long id, CategoriaDTO categoriaDTO){
        Categoria categoria = toCategoria(categoriaDTO);
        categoriaRepository.save(categoria);
        return new MensagemDTO("Categoria atualizada com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) {
        Categoria categoriaBanco = categoriaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Categoria com ID "+id+" nao existe!"));

        List<Produto> categoriaProdutoAtivos = produtoRepository.findProdutoExisteCategoria(categoriaBanco);

        if (!categoriaProdutoAtivos.isEmpty()){
            return new MensagemDTO("Não é possível excluir esse categoria tem produto ativo.", HttpStatus.CREATED);
        } else {
            desativarCategoria(categoriaBanco);
            return new MensagemDTO("Categoria deletada com sucesso!", HttpStatus.CREATED);
        }
    }

    private void desativarCategoria(Categoria categoria) {
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);
    }

    public CategoriaDTO categoriaToDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();

        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setAtivo(categoria.getAtivo());
        categoriaDTO.setNome(categoria.getNome());

        List<ProdutoDTO> listaEnd = new ArrayList<>();

        if(categoria.getProdutos() != null)
            for(int i=0; i<categoria.getProdutos().size(); i++){
                listaEnd.add(produtoToDTO(categoria.getProdutos().get(i)));
            }
        categoriaDTO.setProdutos(listaEnd);

        return categoriaDTO;
    }
    public Categoria toCategoria(CategoriaDTO categoriaDTO){
        Categoria novoCategoria = new Categoria();

        novoCategoria.setId(categoriaDTO.getId());
        novoCategoria.setAtivo(categoriaDTO.getAtivo());
        novoCategoria.setNome(categoriaDTO.getNome());

        List<Produto> listaEnd = new ArrayList<>();
        if(categoriaDTO.getProdutos() != null)
            for(int i=0; i<categoriaDTO.getProdutos().size(); i++){
                listaEnd.add(toProduto(novoCategoria,categoriaDTO.getProdutos().get(i)));

            }

        novoCategoria.setProdutos(listaEnd);

        return novoCategoria;
    }
    public Produto toProduto(Categoria novoCategoria, ProdutoDTO produtoDTO){
        Produto novoProduto = new Produto();

        novoProduto.setId(produtoDTO.getId());
        novoProduto.setAtivo(produtoDTO.getAtivo());
        novoProduto.setNome(produtoDTO.getNome());
        List<Sabor>  listaSabor = new ArrayList<>();
        if(produtoDTO.getSabores() != null)
            for(int i=0; i<produtoDTO.getSabores().size(); i++) {
                listaSabor.add(toSabor(novoProduto, produtoDTO.getSabores().get(i)));
            }
        novoProduto.setSabores(listaSabor);

        Categoria categoria = new Categoria();
        categoria.setId(produtoDTO.getCategoriaId().getId());

        novoProduto.setCategoriaId(categoria);

        return novoProduto;
    }
    public ProdutoDTO produtoToDTO(Produto produto){
        ProdutoDTO novoProduto = new ProdutoDTO();

        novoProduto.setId(produto.getId());
        novoProduto.setAtivo(produto.getAtivo());
        novoProduto.setNome(produto.getNome());

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(produto.getCategoriaId().getId());

        novoProduto.setCategoriaId(categoriaDTO);

        List<SaborDTO> listaSabor = new ArrayList<>();
        if(produto.getSabores()!= null)
            for(int i=0; i<produto.getSabores().size(); i++){
                listaSabor.add(saborToDTO(novoProduto,produto.getSabores().get(i)));
            }
        novoProduto.setSabores(listaSabor);
        return novoProduto;
    }
    public SaborDTO saborToDTO(ProdutoDTO novoProduto, Sabor sabor){
        SaborDTO saborDTO = new SaborDTO();

        saborDTO.setId(sabor.getId());
        saborDTO.setAtivo(sabor.getAtivo());
        saborDTO.setNome(sabor.getNome());

        return saborDTO;
    }
    public Sabor toSabor(Produto novoProduto, SaborDTO saborDTO){
        Sabor novoSabor = new Sabor();

        novoSabor.setId(saborDTO.getId());
        novoSabor.setAtivo(saborDTO.getAtivo());
        novoSabor.setNome(saborDTO.getNome());

        return novoSabor;
    }
}