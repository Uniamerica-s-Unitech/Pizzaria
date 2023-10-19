package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

        return clienteDTO;
    }

    public Cliente toCliente(ClienteDTO clienteDTO){
        Cliente novoCliente = new Cliente();

        novoCliente.setId(clienteDTO.getId());
        novoCliente.setAtivo(clienteDTO.getAtivo());
        novoCliente.setNome(clienteDTO.getNome());

        return novoCliente;
    }
}
