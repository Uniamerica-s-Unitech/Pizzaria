package Pizzaria.ServicesTest;

import Pizzaria.Entiny.Pedido;
import Pizzaria.Service.ClienteService;
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

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Repositorye.ClienteRepository;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindById_ClienteDoesNotExist_ShouldThrowException() {
        Long id = 1L;

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clienteService.findById(id));
    }



    @Test
    public void testEditar_ClienteExists_ShouldReturnEditedCliente() {
        Long id = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Cliente Editado");

        Cliente clienteBanco = new Cliente();
        clienteBanco.setId(id);
        clienteBanco.setNome("Cliente Original");

        when(clienteRepository.existsById(id)).thenReturn(true);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteBanco));
        when(clienteRepository.save(any())).thenReturn(clienteBanco);

        Cliente result = clienteService.editar(id, clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.getNome(), result.getNome());
    }

    @Test
    public void testEditar_ClienteDoesNotExist_ShouldThrowException() {
        Long id = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Cliente Editado");

        when(clienteRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> clienteService.editar(id, clienteDTO));
    }

    @Test
    public void testDelete_ClienteHasPedidos_ShouldThrowException() {
        Long id = 1L;
        Cliente clienteBanco = new Cliente();
        clienteBanco.setId(id);
        clienteBanco.setPedidos(List.of(new Pedido())); // Supondo que Pedido seja a classe que representa pedidos

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteBanco));

        assertThrows(IllegalArgumentException.class, () -> clienteService.delete(id));
    }



}
