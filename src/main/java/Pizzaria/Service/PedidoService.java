package Pizzaria.Service;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.DTO.PedidoDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
    }

    public PedidoDTO findById(Long id) {
        try{
            Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Pdeido nao existe" + id));
            return toPedidoDTO(pedido);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o pdedido."+ex.getMessage(), ex);
        }
    }

    public List<PedidoDTO> listar(){
        return pedidoRepository.findByAtivo().stream().map(this::toPedidoDTO).toList();
    }

    public Pedido cadastrar(PedidoDTO pedidoDTO){
        Pedido pedidonovo = modelMapper.map(pedidoDTO,Pedido.class);
        return pedidoRepository.save(pedidonovo);

        /*Assert.notNull(pedido.getClienteId(), "Cliente inválido");
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
*/
    }

    /*public PedidoDTO editar(Long id,PedidoDTO pedidoDTO){
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
    }*/

    public PedidoDTO toPedidoDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }
}
