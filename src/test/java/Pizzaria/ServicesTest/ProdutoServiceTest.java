package Pizzaria.ServicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.ProdutoRepository;
import Pizzaria.Service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProdutoServiceTest {

    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        produtoService = new ProdutoService(produtoRepository, modelMapper);
    }

    @Test
    void testFindProdutoById() {
        Long produtoId = 1L;
        Produto produto = new Produto();
        produto.setId(produtoId);
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        ProdutoDTO produtoDTO = produtoService.findById(produtoId);

        assertNotNull(produtoDTO);
        assertEquals(produtoId, produtoDTO.getId());
    }

    @Test
    void testFindProdutoByIdNotFound() {
        Long produtoId = 1L;
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> produtoService.findById(produtoId));
    }

    @Test
    void testListProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        Produto produto2 = new Produto();
        produto2.setId(2L);
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);
        when(produtoRepository.findByAtivo()).thenReturn(produtos);

        List<ProdutoDTO> produtoDTOs = produtoService.listar();

        assertNotNull(produtoDTOs);
        assertEquals(2, produtoDTOs.size());
        assertEquals(1L, produtoDTOs.get(0).getId());
        assertEquals(2L, produtoDTOs.get(1).getId());
    }

    @Test
    void testCadastrarProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Pizza Margherita");
        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(1L);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        Produto produtoCadastrado = produtoService.cadastrar(produtoDTO);

        assertNotNull(produtoCadastrado);
        assertEquals(1L, produtoCadastrado.getId());
    }

    @Test
    void testEditarProduto() {
        Long produtoId = 1L;
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Pizza Calabresa");
        Produto produtoBanco = new Produto();
        produtoBanco.setId(produtoId);
        produtoBanco.setNome("Pizza Margherita");
        when(produtoRepository.existsById(produtoId)).thenReturn(true);
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoBanco));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoBanco);

        Produto produtoEditado = produtoService.editar(produtoId, produtoDTO);

        assertNotNull(produtoEditado);
        assertEquals(produtoId, produtoEditado.getId());
        assertEquals("Pizza Calabresa", produtoEditado.getNome());
    }

    @Test
    void testEditarProdutoNotFound() {
        Long produtoId = 1L;
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Pizza Calabresa");
        when(produtoRepository.existsById(produtoId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> produtoService.editar(produtoId, produtoDTO));
    }
}
