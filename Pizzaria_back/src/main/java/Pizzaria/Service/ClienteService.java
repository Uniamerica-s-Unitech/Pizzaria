package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.DTO.MensagemDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public ClienteDTO findCleinteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("cliente  não encontrado!"));
        return clienteToDTO(cliente);
    }

    public List<ClienteDTO> listar() {
        return clienteRepository.findClienteByAtivo().stream().map(this::clienteToDTO).toList();
    }

    public MensagemDTO cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = toCliente(clienteDTO);
        if(cliente.getEnderecos() != null)
            for(int i=0; i<cliente.getEnderecos().size(); i++){
                cliente.getEnderecos().get(i).setClienteId(cliente);
            }
        clienteRepository.save(cliente);
        return new MensagemDTO("Cliente cadastrado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO editarCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = toCliente(clienteDTO);
        if(cliente.getEnderecos() != null)
            for(int i=0; i<cliente.getEnderecos().size(); i++){
                cliente.getEnderecos().get(i).setClienteId(cliente);
            }
        clienteRepository.save(cliente);
        return new MensagemDTO("Cliente atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) {
        Cliente clienteBanco = clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente com ID "+id+" nao existe!"));

        List<Pedido> clientePedidoAtivos = pedidoRepository.findPedidoAbertosPorCliente(clienteBanco);

        if (!clientePedidoAtivos.isEmpty()){
            return new MensagemDTO("Não é possível excluir esse cliente, pois existem pedidos ativos associados a ele.", HttpStatus.NOT_FOUND);
        } else {
            desativarCliente(clienteBanco);
        }
        return new MensagemDTO("Cliente deletado com sucesso!", HttpStatus.CREATED);
    }

    private void desativarCliente(Cliente cliente) {
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    public ClienteDTO clienteToDTO(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setId(cliente.getId());
        clienteDTO.setAtivo(cliente.getAtivo());
        clienteDTO.setNome(cliente.getNome());

        List<EnderecoDTO> listaEnd = new ArrayList<>();
        if(cliente.getEnderecos() != null)
            for(int i=0; i<cliente.getEnderecos().size(); i++){
                listaEnd.add(enderecoToDTO(cliente.getEnderecos().get(i)));
            }

        clienteDTO.setEnderecos(listaEnd);

        return clienteDTO;
    }
    public Cliente toCliente(ClienteDTO clienteDTO){
        Cliente novoCliente = new Cliente();

        novoCliente.setId(clienteDTO.getId());
        novoCliente.setAtivo(clienteDTO.getAtivo());
        novoCliente.setNome(clienteDTO.getNome());

        List<Endereco> listaEnd = new ArrayList<>();
        if(clienteDTO.getEnderecos() != null)
            for(int i=0; i<clienteDTO.getEnderecos().size(); i++){
                listaEnd.add(toEndereco(novoCliente,clienteDTO.getEnderecos().get(i)));
            }

        novoCliente.setEnderecos(listaEnd);
        return novoCliente;
    }
    public Endereco toEndereco(Cliente novoCliente, EnderecoDTO enderecoDTO){
        Endereco novoEndereco = new Endereco();
        if(enderecoDTO.getId() > 0)
            novoEndereco.setId(enderecoDTO.getId());
        novoEndereco.setAtivo(enderecoDTO.getAtivo());
        novoEndereco.setNumero(enderecoDTO.getNumero());
        novoEndereco.setRua(enderecoDTO.getRua());
        novoEndereco.setBairro(enderecoDTO.getBairro());
        novoEndereco.setClienteId(novoCliente);
        return novoEndereco;
    }
    public EnderecoDTO enderecoToDTO(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setAtivo(endereco.getAtivo());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setBairro(endereco.getBairro());
        return enderecoDTO;
    }
}