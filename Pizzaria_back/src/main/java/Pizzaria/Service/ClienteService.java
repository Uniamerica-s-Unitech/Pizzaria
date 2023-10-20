package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public ClienteDTO findCleinteById(Long id) {/**/
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("cliente  não encontrado!"));
        return clienteToDTO(cliente);
    }

    public List<ClienteDTO> listar() {
        return clienteRepository.findClienteByAtivo().stream().map(this::clienteToDTO).toList();
    }

    public String cadastrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = toCliente(clienteDTO);

        Assert.notNull(cliente.getNome(),"Nome inválido!");
        clienteRepository.save(cliente);
        return "Cliente cadastrado com sucesso!";
    }

    public String editarCliente(Long id, ClienteDTO clienteDTO) {
        if (clienteRepository.existsById(id)) {
            Cliente cliente = toCliente(clienteDTO);

            Assert.notNull(cliente.getNome(), "Nome inválido!");

            clienteRepository.save(cliente);
            return "Cliente atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("Cliente não encontrado com o ID fornecido: " + id);
        }
    }
    /*public void deletar(Long id) {
        Cliente clienteBanco = clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente com ID "+id+" nao existe!"));

        List<Pedido> clientePedidoAtivos = pedidoRepository.findPedidoAbertosPorCliente(clienteBanco);

        if (!clientePedidoAtivos.isEmpty()){
            throw new IllegalArgumentException("Não é possível excluir esse cliente tem pedido ativo.");
        } else {
            desativarCliente(clienteBanco);
        }
    }*/

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
                listaEnd.add(toEnderecoDTO(cliente.getEnderecos().get(i)));
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

        novoEndereco.setId(enderecoDTO.getId());
        novoEndereco.setAtivo(enderecoDTO.getAtivo());
        novoEndereco.setNumero(enderecoDTO.getNumero());
        novoEndereco.setRua(enderecoDTO.getRua());
        novoEndereco.setBairro(enderecoDTO.getBairro());
        novoEndereco.setClienteId(novoCliente);
        return novoEndereco;
    }

    public EnderecoDTO toEnderecoDTO(Endereco endereco){
        EnderecoDTO novoEndereco = new EnderecoDTO();

        novoEndereco.setId(endereco.getId());
        novoEndereco.setAtivo(endereco.getAtivo());
        novoEndereco.setNumero(endereco.getNumero());
        novoEndereco.setRua(endereco.getRua());
        novoEndereco.setBairro(endereco.getBairro());
        return novoEndereco;
    }
}
