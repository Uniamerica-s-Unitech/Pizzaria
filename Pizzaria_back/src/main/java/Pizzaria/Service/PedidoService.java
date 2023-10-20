package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.Categoria;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Entiny.Produto;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.CategoriaRepository;
import Pizzaria.Repositorye.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired ProdutoService produtoService;

    public PedidoDTO findPedidoById(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("pedido nao encontrada!"));
        return pedidoToDTO(pedido);
    }

    public List<PedidoDTO> listar(){
        return pedidoRepository.findPedidoByAtivo().stream().map(this::pedidoToDTO).toList();
    }

    public String cadastrarPedido(PedidoDTO pedidoDTO){
        Pedido pedido = toPedido(pedidoDTO);

        pedidoRepository.save(pedido);
        return "pedido Cadastrada com sucesso!";
    }
    public String edtitarPedido(Long id, PedidoDTO pedidoDTO){
        if (pedidoRepository.existsById(id)){
            Pedido pedido = toPedido(pedidoDTO);


            pedidoRepository.save(pedido);
            return "pedido atualizada com sucesso!";

        }else {
            throw new IllegalArgumentException("pedido nao encontrada com o ID fornecido" + id);
        }
    }
    private void desativarPedido(Pedido pedido) {
        pedido.setAtivo(false);
        pedidoRepository.save(pedido);
    }

    public PedidoDTO pedidoToDTO(Pedido pedido){
        PedidoDTO pedidoDTO = new PedidoDTO();

        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setAtivo(pedido.getAtivo());
        pedidoDTO.setSoliciatacao(pedido.getSoliciatacao());
        pedidoDTO.setFinalizacao(pedido.getFinalizacao());

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(pedido.getClienteId().getId());
        pedidoDTO.setClienteId(clienteDTO);

        List<ProdutoDTO> listaProduto = new ArrayList<>();
        if(pedido.getProdutos() != null)
            for(int i=0; i<pedido.getProdutos().size(); i++) {
                listaProduto.add(toProdutoDTO(pedido.getProdutos().get(i)));
            }
        pedidoDTO.setProdutos(listaProduto);


        return pedidoDTO;
    }

    public Produto toProdutoDTO(Produto produto){

        ProdutoDTO novoProduto = new ProdutoDTO();

        novoProduto.setId(produto.getId());
        novoProduto.setAtivo(produto.getAtivo());
        List<Sabor>  listaSabor = new ArrayList<>();
        if(produto.getSabores() != null)
            for(int i=0; i<produto.getSabores().size(); i++) {
                listaSabor.add(toSaborDTO(novoProduto, produto.getSabores().get(i)));
            }
        novoProduto.setSabores(listaSabor);

        Categoria categoria = new Categoria();
        categoria.setId(produtoDTO.getCategoriaId().getId());

        novoProduto.setCategoriaId(categoria);

        return novoProduto;


    }





}
