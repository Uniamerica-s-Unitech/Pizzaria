package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Repositorye.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> listar(){
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO cadastrar(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO,cliente);
        cliente = clienteRepository.save(cliente);
        return convertToDTO(cliente);
    }

    public ClienteDTO editar(Long id,ClienteDTO clienteDTO){
        if (clienteRepository.existsById(id)){
            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if (cliente != null){
                BeanUtils.copyProperties(clienteDTO,cliente,"id");
                cliente = clienteRepository.save(cliente);
                return convertToDTO(cliente);
            }
        }else {
            throw new IllegalArgumentException("Cliente não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void deletar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }
}
