package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Repositorye.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private  ClienteRepository clienteRepository;
    private  ModelMapper modelMapper;

    @Autowired
    private ClienteService clienteService;

    public ClienteService() {
    }


    public ClienteDTO findById(Long id) {
        try{
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente nao existe" + id));
            return toClienteDTO(cliente);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o cliente."+ex.getMessage(), ex);
        }
    }

    public List<ClienteDTO> listar(){
        return clienteRepository.findByAtivo().stream()
                .map(this::toClienteDTO).toList();
    }

    public Cliente cadastrar(ClienteDTO clienteDTO){
        Cliente clienteNovo = modelMapper.map(clienteDTO,Cliente.class);
        return clienteRepository.save(clienteNovo);
    }

    public Cliente editar(Long id, ClienteDTO clienteDTO) {
        if (clienteRepository.existsById(id)) {
            Cliente clienteBanco = clienteRepository.findById(id).orElseThrow(()
                    -> new IllegalArgumentException("Cliente não encontrado com o ID fornecido: " + id));

            clienteBanco.setNome(clienteDTO.getNome());

            return clienteRepository.save(clienteBanco);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado com o ID fornecido: " + id);
        }
    }


    public void delete(Long id) {
        Cliente clienteBanco = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID "+id+" nao existe"));

        if (clienteBanco.getPedidos().isEmpty()){
            desativarCliente(clienteBanco);
        }else {
            throw new IllegalArgumentException("Cliente tem Pedido em andamento nao pode ser deletado");
        }
    }

    private void desativarCliente(Cliente cliente) {
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    public ClienteDTO toClienteDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }
}
