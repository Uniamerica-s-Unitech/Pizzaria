package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.*;
import Pizzaria.Repositorye.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    /*@Autowired
    private PedidoRepository pedidoRepository;*/

    public EnderecoDTO findEnderecoById(Long id) {/**/
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("endereco  não encontrado!"));
        return enderecoToDTO(endereco);
    }

    public List<EnderecoDTO> listar() {
        return enderecoRepository.findEnderecoByAtivo().stream().map(this::enderecoToDTO).toList();
    }

    public String cadastrarEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = toEndereco(enderecoDTO);

       /* Assert.notNull(endereco.getRua(),"Rua inválido!");
        Assert.notNull(endereco.getBairro(),"Bairro inválido!");
        Assert.notNull(endereco.getNumero(),"Numero inválido!");
        Assert.notNull(endereco.getClienteId(),"Cliente inválido!");
        Assert.isTrue(!clienteRepository.findById
                (endereco.getClienteId().getId()).isEmpty(),"Cliente não existe!");*/

        enderecoRepository.save(endereco);
        return "Endereco cadastrado com sucesso!";
    }

    public String editarEndereco(Long id, EnderecoDTO enderecoDTO) {
        if (enderecoRepository.existsById(id)) {
            Endereco endereco = toEndereco(enderecoDTO);

            Assert.notNull(endereco.getRua(),"Rua inválido!");
            Assert.notNull(endereco.getBairro(),"Bairro inválido!");
            Assert.notNull(endereco.getNumero(),"Numero inválido!");
            Assert.notNull(endereco.getClienteId(),"Cliente inválido!");
            Assert.isTrue(!clienteRepository.findById
                    (endereco.getClienteId().getId()).isEmpty(),"Cliente não existe!");

            enderecoRepository.save(endereco);
            return "Endereco atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("Endereco não encontrado com o ID fornecido: " + id);
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

    private void desativarEndereco(Endereco endereco) {
        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

    public EnderecoDTO enderecoToDTO(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setAtivo(endereco.getAtivo());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setBairro(endereco.getBairro());
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(endereco.getClienteId().getId());
        clienteDTO.setAtivo(endereco.getClienteId().getAtivo());
        clienteDTO.setNome(endereco.getClienteId().getNome());
        enderecoDTO.setClienteId(clienteDTO);

        return enderecoDTO;
    }

    public Endereco toEndereco(EnderecoDTO enderecoDTO){
        Endereco novoEndereco = new Endereco();

        novoEndereco.setId(enderecoDTO.getId());
        novoEndereco.setAtivo(enderecoDTO.getAtivo());
        novoEndereco.setNumero(enderecoDTO.getNumero());
        novoEndereco.setRua(enderecoDTO.getRua());
        novoEndereco.setBairro(enderecoDTO.getBairro());

        Cliente cliente = new Cliente();
        cliente.setId(enderecoDTO.getClienteId().getId());

        novoEndereco.setClienteId(cliente);

        return novoEndereco;
    }
}
