package Pizzaria.Service;

import Pizzaria.DTO.ClientePedidoDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.ClientePedido;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClientePedidoRepository;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.PedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientePedidoService {

    @Autowired
    private ClientePedidoRepository clientePedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public ClientePedido findById(Long id){
        return clientePedidoRepository.findById(id).orElse(null);
    }

    public List<ClientePedidoDTO> listar(){
        List<ClientePedido> clientePedidos = clientePedidoRepository.findAll();
        return clientePedidos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientePedidoDTO cadastrar(ClientePedidoDTO clientePedidoDTO){
        ClientePedido clientePedido = new ClientePedido();
        BeanUtils.copyProperties(clientePedidoDTO,clientePedido);

        Assert.notNull(clientePedido.getCliente(), "Cliente inválido");
        Cliente cliente = clienteRepository.findById(clientePedidoDTO.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));


        Assert.notNull(clientePedido.getPedido(), "Pedido inválido");
        Pedido pedido = pedidoRepository.findById(clientePedidoDTO.getPedido().getId())
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));


        clientePedido = clientePedidoRepository.save(clientePedido);
        return convertToDTO(clientePedido);
    }

    private ClientePedidoDTO convertToDTO(ClientePedido clientePedido) {
        ClientePedidoDTO clientePedidoDTO = new ClientePedidoDTO();
        BeanUtils.copyProperties(clientePedido, clientePedidoDTO);
        return clientePedidoDTO;
    }
}
