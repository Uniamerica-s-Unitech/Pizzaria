package Pizzaria.Service;

import Pizzaria.DTO.PedidoDTO;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Entiny.Produto;
import Pizzaria.Repositorye.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

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
        for (Produto produto : pedidonovo.getProdutos()) {
            produto.setPedido(pedidonovo);
        }
        return pedidoRepository.save(pedidonovo);
    }

    public Pedido editar(Long id, PedidoDTO pedidoDTO) {
        if (pedidoRepository.existsById(id)) {
            Pedido pedidoBanco = pedidoRepository.findById(id).orElseThrow(()
                    -> new IllegalArgumentException("Pedido não encontrado com o ID fornecido: " + id));


            return pedidoRepository.save(pedidoBanco);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado com o ID fornecido: " + id);
        }
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

    public PedidoDTO toPedidoDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }
}
