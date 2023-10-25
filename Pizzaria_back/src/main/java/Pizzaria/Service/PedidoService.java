package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.*;
import Pizzaria.Repositorye.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public PedidoDTO findPedidoById(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("pedido nao encontrada!"));
        return pedidoToDTO(pedido);
    }

    public List<PedidoDTO> buscarTicketsAbertos(){
        return pedidoRepository.findPedidosAbertos().stream().map(this::pedidoToDTO).toList();
    }

    public List<PedidoDTO> buscarHistorico() {
        return pedidoRepository.findHistorico().stream().map(this::pedidoToDTO).toList();
    }

    public MensagemDTO cadastrarPedido(PedidoDTO pedidoDTO){
        Pedido pedido = toPedido(pedidoDTO);

        pedidoRepository.save(pedido);


        pedidoRepository.save(pedido);

        return new MensagemDTO("Pedido cadastrado com sucesso!", HttpStatus.CREATED);
    }
    public MensagemDTO editarPedido(Long id, PedidoDTO pedidoDTO){
        Pedido pedido = toPedido(pedidoDTO);
       

        pedidoRepository.save(pedido);
        return new MensagemDTO("Pedido atualizado com sucesso!", HttpStatus.CREATED);
    }

    public PedidoDTO pedidoToDTO(Pedido pedido){
        PedidoDTO pedidoDTO = new PedidoDTO();

        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setAtivo(pedido.getAtivo());
        pedidoDTO.setSoliciatacao(pedido.getSoliciatacao());
        pedidoDTO.setFinalizacao(pedido.getFinalizacao());
        pedidoDTO.setValorTotal(pedido.getValorTotal());

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(pedido.getClienteId().getId());
        pedidoDTO.setClienteId(clienteDTO);

        List<ProdutoDTO> listaProduto = new ArrayList<>();
        if(pedido.getProdutos() != null)
            for(int i=0; i<pedido.getProdutos().size(); i++) {
                listaProduto.add(produtoToDTO(pedido.getProdutos().get(i)));
            }
        pedidoDTO.setProdutos(listaProduto);

        return pedidoDTO;
    }
    public ProdutoDTO produtoToDTO(Produto produto){

        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setId(produto.getId());
        produtoDTO.setAtivo(produto.getAtivo());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setValor(produto.getValor());

        List<SaborDTO>  listaSabor = new ArrayList<>();
        if(produto.getSabores() != null)
            for(int i=0; i<produto.getSabores().size(); i++) {
                listaSabor.add(saborToDTO(produto.getSabores().get(i)));
            }

        produtoDTO.setSabores(listaSabor);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(produto.getCategoriaId().getId());

        produtoDTO.setCategoriaId(categoriaDTO);

        return produtoDTO;
    }
    public SaborDTO saborToDTO(Sabor sabor){
        SaborDTO saborDTO = new SaborDTO();

        saborDTO.setId(sabor.getId());
        saborDTO.setAtivo(sabor.getAtivo());
        saborDTO.setNome(sabor.getNome());

        return saborDTO;
    }
    public Pedido toPedido(PedidoDTO pedidoDTO){
        Pedido novoPedido = new Pedido();

        novoPedido.setId(pedidoDTO.getId());
        novoPedido.setAtivo(pedidoDTO.getAtivo());
        novoPedido.setSoliciatacao(pedidoDTO.getSoliciatacao());
        novoPedido.setFinalizacao(pedidoDTO.getFinalizacao());
        novoPedido.setValorTotal(pedidoDTO.getValorTotal());

            Cliente cliente = new Cliente();
            cliente.setId(pedidoDTO.getClienteId().getId());
            cliente.setAtivo(pedidoDTO.getClienteId().getAtivo());
            cliente.setNome(pedidoDTO.getClienteId().getNome());
            List<Endereco> listaEnd = new ArrayList<>();
            if(pedidoDTO.getClienteId().getEnderecos() != null)
                for(int i=0; i<pedidoDTO.getClienteId().getEnderecos().size(); i++){
                    listaEnd.add(toEndereco(novoPedido,pedidoDTO.getClienteId().getEnderecos().get(i)));
                }
            cliente.setEnderecos(listaEnd);
        novoPedido.setClienteId(cliente);

            List<Produto> listProdutos = new ArrayList<>();
            if(pedidoDTO.getProdutos() != null)
                for(int i=0; i<pedidoDTO.getProdutos().size(); i++){
                    listProdutos.add(toProduto(novoPedido,pedidoDTO.getProdutos().get(i)));
                }
        novoPedido.setProdutos(listProdutos);

        return novoPedido;
    }
    public Endereco toEndereco(Pedido novoPedido, EnderecoDTO enderecoDTO){
        Endereco novoEndereco = new Endereco();

        novoEndereco.setId(enderecoDTO.getId());
        novoEndereco.setAtivo(enderecoDTO.getAtivo());
        novoEndereco.setNumero(enderecoDTO.getNumero());
        novoEndereco.setRua(enderecoDTO.getRua());
        novoEndereco.setBairro(enderecoDTO.getBairro());
        return novoEndereco;
    }
    public Produto toProduto(Pedido novoPedido, ProdutoDTO produtoDTO){
        Produto novoProduto = new Produto();

        novoProduto.setId(produtoDTO.getId());
        novoProduto.setAtivo(produtoDTO.getAtivo());
        novoProduto.setNome(produtoDTO.getNome());
        novoProduto.setValor(produtoDTO.getValor());
        List<Sabor>  listaSabor = new ArrayList<>();
        if(produtoDTO.getSabores() != null)
            for(int i=0; i<produtoDTO.getSabores().size(); i++) {
                listaSabor.add(toSabor(novoProduto, produtoDTO.getSabores().get(i)));
            }
        novoProduto.setSabores(listaSabor);

        Categoria categoria = new Categoria();
        categoria.setId(produtoDTO.getCategoriaId().getId());

        novoProduto.setCategoriaId(categoria);

        return novoProduto;
    }
    public Sabor toSabor(Produto novoProduto,SaborDTO saborDTO){
        Sabor novoSabor = new Sabor();

        novoSabor.setId(saborDTO.getId());
        novoSabor.setAtivo(saborDTO.getAtivo());
        novoSabor.setNome(saborDTO.getNome());

        return novoSabor;
    }






}