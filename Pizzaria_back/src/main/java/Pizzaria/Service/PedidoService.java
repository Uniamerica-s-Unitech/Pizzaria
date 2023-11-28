package Pizzaria.Service;

import Pizzaria.DTO.*;
import Pizzaria.Entiny.*;
import Pizzaria.Repositorye.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


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
        if(pedido.getPedidoProdutoList() != null)
            for(int i=0; i<pedido.getPedidoProdutoList().size(); i++){
                pedido.getPedidoProdutoList().get(i).setPedidoId(pedido);
            }

        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");

        // Formata a data como uma string e salva no campo solicitacao
        pedido.setSolicitacao(dt.format(df));

        pedidoRepository.save(pedido);
        return new MensagemDTO("Pedido cadastrado com sucesso!", HttpStatus.CREATED);
    }
    public MensagemDTO editarPedido(Long id, PedidoDTO pedidoDTO){
        Pedido pedido = toPedido(pedidoDTO);
        if(pedido.getPedidoProdutoList() != null)
            for(int i=0; i<pedido.getPedidoProdutoList().size(); i++){
                pedido.getPedidoProdutoList().get(i).setPedidoId(pedido);
            }
        pedidoRepository.save(pedido);
        return new MensagemDTO("Pedido atualizado com sucesso!", HttpStatus.CREATED);
    }

    public PedidoDTO pedidoToDTO(Pedido pedido){
        PedidoDTO pedidoDTO = new PedidoDTO();

        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setAtivo(pedido.getAtivo());
        pedidoDTO.setSolicitacao(pedido.getSolicitacao());
        pedidoDTO.setFinalizacao(pedido.getFinalizacao());
        pedidoDTO.setValorTotal(pedido.getValorTotal());
        pedidoDTO.setEnderecoId(enderecoToDTO(pedido.getEnderecoId()));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(pedido.getClienteId().getId());
        clienteDTO.setNome(pedido.getClienteId().getNome());
        List<EnderecoDTO> listaEnd = new ArrayList<>();
        if(pedido.getClienteId().getEnderecos() != null)
            for(int i=0; i<pedido.getClienteId().getEnderecos().size(); i++){
                listaEnd.add(enderecoToDTO(pedido.getClienteId().getEnderecos().get(i)));
            }
        clienteDTO.setEnderecos(listaEnd);
        pedidoDTO.setClienteId(clienteDTO);

        List<PedidoProdutoDTO> listaProduto = new ArrayList<>();
        if(pedido.getPedidoProdutoList() != null)
            for(int i=0; i<pedido.getPedidoProdutoList().size(); i++) {
                listaProduto.add(pedidoProdutoToDTO(pedido.getPedidoProdutoList().get(i)));
            }
        pedidoDTO.setPedidoProdutoList(listaProduto);

        return pedidoDTO;
    }
    public PedidoProdutoDTO pedidoProdutoToDTO(PedidoProduto pedidoProduto){

        PedidoProdutoDTO pedidoProdutoDTO = new PedidoProdutoDTO();

        pedidoProdutoDTO.setId(pedidoProduto.getId());
        pedidoProdutoDTO.setAtivo(pedidoProduto.getAtivo());

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedidoDTO.getId());
        pedidoProdutoDTO.setPedidoId(pedidoDTO);


        pedidoProdutoDTO.setProdutoId(produtoToDTO(pedidoProduto.getProdutoId()));

        List<SaborDTO> listaSabores = new ArrayList<>();
        if(pedidoProduto.getSabores() != null)
            for(int i=0; i<pedidoProduto.getSabores().size(); i++) {
                listaSabores.add(saborToDTO(pedidoProduto.getSabores().get(i)));
            }
        pedidoProdutoDTO.setSabores(listaSabores);

        return pedidoProdutoDTO;
    }
    public ProdutoDTO produtoToDTO(Produto produto){
        ProdutoDTO produtoDTO = new ProdutoDTO();

        produtoDTO.setId(produto.getId());
        produtoDTO.setAtivo(produto.getAtivo());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setValor(produto.getValor());
        produtoDTO.setTemSabores(produto.getTemSabores());
        produtoDTO.setTamanho(produto.getTamanho());

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(produto.getCategoriaId().getId());
        categoriaDTO.setAtivo(produto.getCategoriaId().getAtivo());
        categoriaDTO.setNome(produto.getCategoriaId().getNome());

        produtoDTO.setCategoriaId(categoriaDTO);

        return produtoDTO;
    }
    public Produto toProduto(ProdutoDTO produtoDTO){
        Produto novoProduto = new Produto();

        novoProduto.setId(produtoDTO.getId());
        novoProduto.setAtivo(produtoDTO.getAtivo());
        novoProduto.setNome(produtoDTO.getNome());
        novoProduto.setValor(produtoDTO.getValor());
        novoProduto.setTemSabores(produtoDTO.getTemSabores());
        novoProduto.setTamanho(produtoDTO.getTamanho());

        if (produtoDTO.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(produtoDTO.getCategoriaId().getId());

            novoProduto.setCategoriaId(categoria);
        }

        return novoProduto;
    }
    public SaborDTO saborToDTO(Sabor sabor){
        SaborDTO saborDTO = new SaborDTO();

        saborDTO.setId(sabor.getId());
        saborDTO.setAtivo(sabor.getAtivo());
        saborDTO.setNome(sabor.getNome());

        return saborDTO;
    }
    public EnderecoDTO enderecoToDTO(Endereco endereco){
        EnderecoDTO novoEndereco = new EnderecoDTO();

        novoEndereco.setId(endereco.getId());
        novoEndereco.setAtivo(endereco.getAtivo());
        novoEndereco.setNumero(endereco.getNumero());
        novoEndereco.setRua(endereco.getRua());
        novoEndereco.setBairro(endereco.getBairro());
        return novoEndereco;
    }
    public Pedido toPedido(PedidoDTO pedidoDTO){
        Pedido novoPedido = new Pedido();

        novoPedido.setId(pedidoDTO.getId());
        novoPedido.setAtivo(pedidoDTO.getAtivo());
        novoPedido.setSolicitacao(pedidoDTO.getSolicitacao());
        novoPedido.setFinalizacao(pedidoDTO.getFinalizacao());
        novoPedido.setValorTotal(pedidoDTO.getValorTotal());
        novoPedido.setEnderecoId(toEndereco(pedidoDTO.getEnderecoId()));


        Cliente cliente = new Cliente();
        cliente.setId(pedidoDTO.getClienteId().getId());
        cliente.setAtivo(pedidoDTO.getClienteId().getAtivo());
        cliente.setNome(pedidoDTO.getClienteId().getNome());
        List<Endereco> listaEnd = new ArrayList<>();
        if(pedidoDTO.getClienteId().getEnderecos() != null)
            for(int i=0; i<pedidoDTO.getClienteId().getEnderecos().size(); i++){
                listaEnd.add(toEndereco(pedidoDTO.getClienteId().getEnderecos().get(i)));
            }
        cliente.setEnderecos(listaEnd);
        novoPedido.setClienteId(cliente);

        List<PedidoProduto> listProdutos = new ArrayList<>();
        if(pedidoDTO.getPedidoProdutoList() != null)
            for(int i=0; i<pedidoDTO.getPedidoProdutoList().size(); i++){
                listProdutos.add(toPedidoProduto(novoPedido,pedidoDTO.getPedidoProdutoList().get(i)));
            }
        novoPedido.setPedidoProdutoList(listProdutos);

        return novoPedido;
    }
    public PedidoProduto toPedidoProduto(Pedido novoPedido, PedidoProdutoDTO pedidoProdutoDTO){

        PedidoProduto novoPedidoProduto = new PedidoProduto();

        if(pedidoProdutoDTO.getId() != null)
            novoPedidoProduto.setId(pedidoProdutoDTO.getId());

        novoPedidoProduto.setAtivo(pedidoProdutoDTO.getAtivo());

        if(pedidoProdutoDTO.getPedidoId() != null) {
            Pedido pedido = new Pedido();
            pedido.setId(pedidoProdutoDTO.getPedidoId().getId());
            novoPedidoProduto.setPedidoId(pedido);
        }

        novoPedidoProduto.setProdutoId(toProduto(pedidoProdutoDTO.getProdutoId()));

        List<Sabor> listSabores = new ArrayList<>();
        if(pedidoProdutoDTO.getSabores() != null)
            for(int i=0; i<pedidoProdutoDTO.getSabores().size(); i++){
                listSabores.add(toSabor(novoPedido,pedidoProdutoDTO.getSabores().get(i)));
            }
        novoPedidoProduto.setSabores(listSabores);


        return novoPedidoProduto;
    }
    public Endereco toEndereco(EnderecoDTO enderecoDTO){
        Endereco novoEndereco = new Endereco();

        novoEndereco.setId(enderecoDTO.getId());
        novoEndereco.setAtivo(enderecoDTO.getAtivo());
        novoEndereco.setNumero(enderecoDTO.getNumero());
        novoEndereco.setRua(enderecoDTO.getRua());
        novoEndereco.setBairro(enderecoDTO.getBairro());
        return novoEndereco;
    }
    public Sabor toSabor(Pedido novoPedido,SaborDTO saborDTO){
        Sabor novoSabor = new Sabor();

        novoSabor.setId(saborDTO.getId());
        novoSabor.setAtivo(saborDTO.getAtivo());
        novoSabor.setNome(saborDTO.getNome());

        return novoSabor;
    }
}