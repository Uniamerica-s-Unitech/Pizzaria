package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.SaborRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SaborService {

    @Autowired
    private SaborRepository saborRepository;

    public SaborDTO findSaborById(Long id) {/**/
        Sabor sabor = saborRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("sabor  não encontrado!"));
        return saborToDTO(sabor);
    }

    public List<SaborDTO> listar() {
        return saborRepository.findSaborByAtivo().stream().map(this::saborToDTO).toList();
    }

    public String cadastrarSabor(SaborDTO saborDTO) {
        Sabor sabor = toSabor(saborDTO);

       /* Assert.notNull(endereco.getRua(),"Rua inválido!");
        Assert.notNull(endereco.getBairro(),"Bairro inválido!");
        Assert.notNull(endereco.getNumero(),"Numero inválido!");
        Assert.notNull(endereco.getClienteId(),"Cliente inválido!");
        Assert.isTrue(!clienteRepository.findById
                (endereco.getClienteId().getId()).isEmpty(),"Cliente não existe!");*/

        saborRepository.save(sabor);
        return "sabor cadastrado com sucesso!";
    }
    public String editarSabor(Long id, SaborDTO saborDTO) {
        if (saborRepository.existsById(id)) {
            Sabor sabor = toSabor(saborDTO);

            Assert.notNull(sabor.getNome(),"Nome inválido!");

            saborRepository.save(sabor);
            return "sabor atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("sabor não encontrado com o ID fornecido: " + id);
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
    private void desativarSabor(Sabor sabor) {
        sabor.setAtivo(false);
        saborRepository.save(sabor);
    }
    public SaborDTO saborToDTO(Sabor sabor){
        SaborDTO saborDTO = new SaborDTO();

        saborDTO.setId(sabor.getId());
        saborDTO.setAtivo(sabor.getAtivo());

        saborDTO.setNome(sabor.getNome());

        return saborDTO;
    }
    public Sabor toSabor(SaborDTO saborDTO){
        Sabor novoSabor = new Sabor();

        novoSabor.setId(saborDTO.getId());
        novoSabor.setAtivo(saborDTO.getAtivo());
        novoSabor.setNome(saborDTO.getNome());


        return novoSabor;
    }

}
