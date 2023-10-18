package Pizzaria.ServicesTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Pizzaria.DTO.PedidoDTO;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.PedidoRepository;
import Pizzaria.Service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PedidoServiceTest {

    private PedidoService pedidoService;
    private PedidoRepository pedidoRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        pedidoRepository = mock(PedidoRepository.class);
        modelMapper = mock(ModelMapper.class);
        pedidoService = new PedidoService(pedidoRepository, modelMapper);
    }

    @Test
    void testFindByIdWhenPedidoExists() {
        Long pedidoId = 1L;
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedidoId);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(modelMapper.map(pedido, PedidoDTO.class)).thenReturn(pedidoDTO);

        PedidoDTO result = pedidoService.findById(pedidoId);

        assertNotNull(result);
        assertEquals(pedidoId, result.getId());
    }

    @Test
    void testFindByIdPedidoDoesNoExist() {
        Long pedidoId = 1L;

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> pedidoService.findById(pedidoId));
    }

    @Test
    void testListarPedidosExist() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        when(pedidoRepository.findByAtivo()).thenReturn(List.of(pedido1, pedido2));

        List<PedidoDTO> result = pedidoService.listar();

        assertEquals(2, result.size());
    }

    @Test
    void testListarNoPedidosExist() {
        when(pedidoRepository.findByAtivo()).thenReturn(Collections.emptyList());

        List<PedidoDTO> result = pedidoService.listar();

        assertTrue(result.isEmpty());
    }

    @Test
    void testCadastrarPedido() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        Pedido pedido = new Pedido();

        when(modelMapper.map(pedidoDTO, Pedido.class)).thenReturn(pedido);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido result = pedidoService.cadastrar(pedidoDTO);

        assertNotNull(result);
    }



}
