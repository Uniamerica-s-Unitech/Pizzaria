package Pizzaria.ServicesTest;

import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Repositorye.EnderecoRepository;
import Pizzaria.Service.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        enderecoService = new EnderecoService(enderecoRepository, new ModelMapper());
    }


    @Test
    public void testFindEnderecoByIdNoFound() {
        Long id = 1L;
        when(enderecoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> enderecoService.findById(id));
    }

    @Test
    public void testDeleteEnderecoNoFound() {
        Long id = 1L;
        when(enderecoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> enderecoService.delete(id));
    }



}
