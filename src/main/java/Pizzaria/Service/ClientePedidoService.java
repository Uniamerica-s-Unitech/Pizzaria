package Pizzaria.Service;

import Pizzaria.DTO.ClientePedidoDTO;
import Pizzaria.Entiny.ClientePedido;
import Pizzaria.Repositorye.ClientePedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientePedidoService {

    @Autowired
    private ClientePedidoRepository clientePedidoRepository;

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
        clientePedido = clientePedidoRepository.save(clientePedido);
        return convertToDTO(clientePedido);
    }

    private ClientePedidoDTO convertToDTO(ClientePedido clientePedido) {
        ClientePedidoDTO clientePedidoDTO = new ClientePedidoDTO();
        BeanUtils.copyProperties(clientePedido, clientePedidoDTO);
        return clientePedidoDTO;
    }
}
