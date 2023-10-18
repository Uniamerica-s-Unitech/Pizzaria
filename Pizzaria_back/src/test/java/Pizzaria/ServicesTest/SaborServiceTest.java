package Pizzaria.ServicesTest;

import Pizzaria.Service.SaborService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.SaborRepository;

public class SaborServiceTest {

    @InjectMocks
    private SaborService saborService;

    @Mock
    private SaborRepository saborRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }





    @Test
    public void testEditar_SaborDoesNotExist() {
        // Arrange
        Long id = 1L;
        SaborDTO saborDTO = new SaborDTO();

        when(saborRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> saborService.editar(id, saborDTO));
    }
    @Test
    public void testEditar_SaborExists() {
        // Arrange
        Long id = 1L;
        SaborDTO saborDTO = new SaborDTO();
        saborDTO.setNome("Sabor Editado");
        Sabor saborBanco = new Sabor();
        saborBanco.setId(id);

        when(saborRepository.existsById(id)).thenReturn(true);
        when(saborRepository.findById(id)).thenReturn(Optional.of(saborBanco));
        when(saborRepository.save(any(Sabor.class))).thenReturn(saborBanco);

        // Act
        Sabor result = saborService.editar(id, saborDTO);

        // Assert
        assertNotNull(result);
        assertEquals(saborDTO.getNome(), result.getNome());
    }


}
