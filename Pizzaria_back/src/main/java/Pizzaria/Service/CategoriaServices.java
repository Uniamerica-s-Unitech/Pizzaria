package Pizzaria.Service;


import Pizzaria.DTO.CategoriaDTO;
import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Categoria;
import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaServices {

    @Autowired
    private CategoriaRepository categoriaRepository;


    public CategoriaDTO findCategoriaById(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada!"));
        return categoriaToDTO(categoria);
    }

    public List<CategoriaDTO> listar(){
        return categoriaRepository.findCategoriaByAtivo().stream().map(this::categoriaToDTO).toList();
    }

    public String cadastrarCategoria(CategoriaDTO categoriaDTO){
        Categoria categoria = toCategoria(categoriaDTO);

        Assert.notNull(categoria.getNome(),"Nome Invalido");
        categoriaRepository.save(categoria);
        return "Catogria Cadastrada com sucesso!";
    }
    public String edtitarCategoria(Long id, CategoriaDTO categoriaDTO){
        if (categoriaRepository.existsById(id)){
            Categoria categoria = toCategoria(categoriaDTO);

            Assert.notNull(categoria.getNome(), "Nome inválido!");

            categoriaRepository.save(categoria);
            return "Categoria atualizada com sucesso!";

        }else {
            throw new IllegalArgumentException("Categoria nao encontrada com o ID fornecido" + id);
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
                listaEnd.add(toProdutoDTO(categoria.getProdutos().get(i)));
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
        novoProduto.setSabores(produtoDTO.getSabores());
        novoProduto.setCategoriaId(produtoDTO.getCategoriaId());
        return novoProduto;
    }
    public ProdutoDTO toProdutoDTO(Produto produto){
        ProdutoDTO novoProduto = new ProdutoDTO();

        novoProduto.setId(produto.getId());
        novoProduto.setAtivo(produto.getAtivo());
        novoProduto.setCategoriaId(produto.getCategoriaId());
        return novoProduto;
    }


}
