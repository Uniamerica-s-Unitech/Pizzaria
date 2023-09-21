package Pizzaria.ServicesTest;

import Pizzaria.Service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.ProdutoRepository;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindById_ProdutoDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> produtoService.findById(id));
    }



    @Test
    public void testEditar_ProdutoExists() {
        // Arrange
        Long id = 1L;
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto Editado");
        Produto produtoBanco = new Produto();
        produtoBanco.setId(id);

        when(produtoRepository.existsById(id)).thenReturn(true);
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoBanco));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoBanco);

        // Act
        Produto result = produtoService.editar(id, produtoDTO);

        // Assert
        assertNotNull(result);
        assertEquals(produtoDTO.getNome(), result.getNome());
    }



    @Test
    public void testEditar_ProdutoDoesNotExist() {
        // Arrange
        Long id = 1L;
        ProdutoDTO produtoDTO = new ProdutoDTO();

        when(produtoRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> produtoService.editar(id, produtoDTO));
    }


}
