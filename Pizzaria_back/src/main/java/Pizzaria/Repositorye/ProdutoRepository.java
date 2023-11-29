package Pizzaria.Repositorye;

import Pizzaria.Entiny.Categoria;
import Pizzaria.Entiny.PedidoProduto;
import Pizzaria.Entiny.Produto;
import Pizzaria.Entiny.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    @Query("FROM Produto WHERE ativo = true")
    List<Produto> findProdutoByAtivo();

    @Query("FROM Produto WHERE ativo = true AND categoriaId = :categoria")
    List<Produto> findProdutoExisteCategoria(Categoria categoria);

    @Query("FROM PedidoProduto WHERE ativo = true AND :sabor MEMBER OF sabores")
    List<PedidoProduto> findProdutoExisteSabores(Sabor sabor);
}