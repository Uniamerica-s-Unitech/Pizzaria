package Pizzaria.ServicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.SaborRepository;
import Pizzaria.Service.SaborService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SaborServiceTest {

    private SaborService saborService;

    @MockBean
    private SaborRepository saborRepository;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        saborService = new SaborService(saborRepository, modelMapper);
    }

    @Test
    void testFindSaborById() {
        Long saborId = 1L;
        Sabor sabor = new Sabor();
        sabor.setId(saborId);
        when(saborRepository.findById(saborId)).thenReturn(Optional.of(sabor));

        SaborDTO saborDTO = saborService.findById(saborId);

        assertNotNull(saborDTO);
        assertEquals(saborId, saborDTO.getId());
    }

    @Test
    void testFindSaborByIdNotFound() {
        Long saborId = 1L;
        when(saborRepository.findById(saborId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> saborService.findById(saborId));
    }

    @Test
    void testListSabores() {
        Sabor sabor1 = new Sabor();
        sabor1.setId(1L);
        Sabor sabor2 = new Sabor();
        sabor2.setId(2L);
        List<Sabor> sabores = new ArrayList<>();
        sabores.add(sabor1);
        sabores.add(sabor2);
        when(saborRepository.findByAtivo()).thenReturn(sabores);

        List<SaborDTO> saborDTOs = saborService.listar();

        assertNotNull(saborDTOs);
        assertEquals(2, saborDTOs.size());
        assertEquals(1L, saborDTOs.get(0).getId());
        assertEquals(2L, saborDTOs.get(1).getId());
    }

    @Test
    void testCadastrarSabor() {
        // Arrange
        SaborDTO saborDTO = new SaborDTO();
        saborDTO.setNome("Calabresa");
        Sabor saborSalvo = new Sabor();
        saborSalvo.setId(1L);
        when(saborRepository.save(any(Sabor.class))).thenReturn(saborSalvo);

        Sabor saborCadastrado = saborService.cadastrar(saborDTO);

        assertNotNull(saborCadastrado);
        assertEquals(1L, saborCadastrado.getId());
    }

    @Test
    void testEditarSabor() {
        Long saborId = 1L;
        SaborDTO saborDTO = new SaborDTO();
        saborDTO.setNome("Mussarela");
        Sabor saborBanco = new Sabor();
        saborBanco.setId(saborId);
        saborBanco.setNome("Margherita");
        when(saborRepository.existsById(saborId)).thenReturn(true);
        when(saborRepository.findById(saborId)).thenReturn(Optional.of(saborBanco));
        when(saborRepository.save(any(Sabor.class))).thenReturn(saborBanco);

        Sabor saborEditado = saborService.editar(saborId, saborDTO);

        assertNotNull(saborEditado);
        assertEquals(saborId, saborEditado.getId());
        assertEquals("Mussarela", saborEditado.getNome());
    }

    @Test
    void testEditarSaborNotFound() {
        Long saborId = 1L;
        SaborDTO saborDTO = new SaborDTO();
        saborDTO.setNome("Mussarela");
        when(saborRepository.existsById(saborId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> saborService.editar(saborId, saborDTO));
    }



    @Test
    void testDeleteSaborNotFound() {
        Long saborId = 1L;
        when(saborRepository.findById(saborId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> saborService.delete(saborId));
    }
}
