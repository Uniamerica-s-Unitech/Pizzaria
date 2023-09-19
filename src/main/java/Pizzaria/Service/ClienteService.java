package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
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

    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public List<ClienteDTO> listar(){
        List<Cliente> clientes = clienteRepository.findByAtivo();
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

    public void dezAtivar(Long id,Cliente cliente) {
        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if (clienteBanco != null){
            List<Pedido> clienteAtivo = clienteRepository.findClienteAtivoPedido(clienteBanco);
            if (clienteAtivo.isEmpty()) {
                cliente.setAtivo(false);
                clienteRepository.save(cliente);
            } else {
                throw new IllegalArgumentException("Cliente possui pedidos ativos e não pode ser desativado.");
            }
        }else {
            throw new IllegalArgumentException("Cliente não encontrado com o ID: " + id);
        }
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }
}
