package Pizzaria.Service;

import Pizzaria.DTO.PedidoDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.PedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Pedido findById(Long id){
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<PedidoDTO> listar(){
        List<Pedido> pedidos = pedidoRepository.findByAtivo();
        return pedidos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO cadastrar(PedidoDTO pedidoDTO){
        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(pedidoDTO,pedido);

        Assert.notNull(pedido.getClienteId(), "Cliente inválido");
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));


        pedido = pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    public PedidoDTO editar(Long id,PedidoDTO pedidoDTO){
        if (pedidoRepository.existsById(id)){
            Pedido pedido = pedidoRepository.findById(id).orElse(null);
            if (pedido != null){
                BeanUtils.copyProperties(pedidoDTO,pedido,"id");
                pedido = pedidoRepository.save(pedido);
                return convertToDTO(pedido);
            }
        }else {
            throw new IllegalArgumentException("Pedido não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void dezAtivar(Long id, Pedido pedido) {
        Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);
        if (pedidoBanco != null){
            pedido.setAtivo(false);
            pedidoRepository.save(pedido);
        }else {
            throw new IllegalArgumentException("Pedido não encontrado com o ID: " + id);
        }
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        BeanUtils.copyProperties(pedido, pedidoDTO);
        return pedidoDTO;
    }
}
