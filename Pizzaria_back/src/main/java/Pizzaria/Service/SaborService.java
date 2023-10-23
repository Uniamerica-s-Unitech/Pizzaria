package Pizzaria.Service;

import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Entiny.Produto;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.PedidoRepository;
import Pizzaria.Repositorye.ProdutoRepository;
import Pizzaria.Repositorye.SaborRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String cadastrarSabor(SaborDTO saborDTO) {
        Sabor sabor = toSabor(saborDTO);
        saborRepository.save(sabor);
        return "Sabor cadastrado com sucesso!";
    }
    public String editarSabor(Long id, SaborDTO saborDTO) {
        if (saborRepository.existsById(id)) {
            Sabor sabor = toSabor(saborDTO);

            Assert.notNull(sabor.getNome(),"Nome inválido!");

            saborRepository.save(sabor);
            return "Sabor atualizado com sucesso!";

        }else {
            throw new IllegalArgumentException("Sabor não encontrado com o ID fornecido: " + id);
        }
    }

    public void deletar(Long id) {
        Sabor saborBanco = saborRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sabor com ID " + id + " não existe!"));

        List<Produto> saborProdutoAtivos = produtoRepository.findProdutoExisteSabores(saborBanco);

        if (!saborProdutoAtivos.isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir esse Sabor, pois existem produtos ativos associados a ele.");
        } else {
            desativarSabor(saborBanco);
        }
    }

    private void desativarSabor(Sabor sabor) {
        sabor.setAtivo(false);
        saborRepository.save(sabor);
    }
    /*public void deletar(Long id) {
        Sabor saborBanco = saborRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Sabor com ID "+id+" nao existe!"));

        List<Produto> saborProdutoAtivos = produtoRepository.findProdutoExisteSabores(saborBanco);

        if (!saborProdutoAtivos.isEmpty()){
            throw new IllegalArgumentException("Não é possível excluir esse Sabor tem produto ativo.");
        } else {
            desativarSabor(saborBanco);
        }
    }
    private void desativarSabor(Sabor sabor) {
        sabor.setAtivo(false);
        saborRepository.save(sabor);
    }*/
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
