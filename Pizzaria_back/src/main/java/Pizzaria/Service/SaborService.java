package Pizzaria.Service;

import Pizzaria.DTO.MensagemDTO;
import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Entiny.Produto;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.PedidoRepository;
import Pizzaria.Repositorye.ProdutoRepository;
import Pizzaria.Repositorye.SaborRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SaborService {

    @Autowired
    private SaborRepository saborRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public SaborDTO findSaborById(Long id) {
        Sabor sabor = saborRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sabor não encontrado!"));
        return saborToDTO(sabor);
    }

    public List<SaborDTO> listar() {
        return saborRepository.findSaborByAtivo().stream().map(this::saborToDTO).toList();
    }

    public MensagemDTO cadastrarSabor(SaborDTO saborDTO) {
        Sabor sabor = toSabor(saborDTO);
        saborRepository.save(sabor);
        return new MensagemDTO("Sabor cadastrado com sucesso!", HttpStatus.CREATED);
    }
    public MensagemDTO editarSabor(Long id, SaborDTO saborDTO) {
        Sabor sabor = toSabor(saborDTO);
        saborRepository.save(sabor);
        return new MensagemDTO("Sabor atualizado com sucesso!", HttpStatus.CREATED);
    }

    public MensagemDTO deletar(Long id) {
        Sabor saborBanco = saborRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sabor com ID " + id + " não existe!"));

        List<Produto> saborProdutoAtivos = produtoRepository.findProdutoExisteSabores(saborBanco);

        if (!saborProdutoAtivos.isEmpty()) {
            return new MensagemDTO("Não é possível excluir esse Sabor, pois existem produtos ativos associados a ele.", HttpStatus.CREATED);
        } else {
            desativarSabor(saborBanco);
        }
        return new MensagemDTO("Não é possível", HttpStatus.CREATED);
    }

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