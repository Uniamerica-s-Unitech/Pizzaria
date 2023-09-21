package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,
                          ModelMapper modelMapper){
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
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
        return clienteRepository.findAll().stream().map(this::toClienteDTO).toList();
    }

    public Cliente cadastrar(ClienteDTO clienteDTO){
        Cliente clienteNovo = modelMapper.map(clienteDTO,Cliente.class);
        return clienteRepository.save(clienteNovo);
    }

    /*public String updateCliente(ClienteDTO clienteDTO) {

        Cliente clienteNovo = toCliente(clienteDTO);

        clienteRepository.save(clienteNovo);

        return "Cliente editado com sucesso";
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
    }*/

    public ClienteDTO toClienteDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }


}
