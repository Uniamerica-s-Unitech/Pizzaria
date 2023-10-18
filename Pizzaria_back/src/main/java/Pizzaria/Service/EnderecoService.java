package Pizzaria.Service;

import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Repositorye.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, 
                           ModelMapper modelMapper) {
        this.enderecoRepository = enderecoRepository;
        this.modelMapper = modelMapper;
    }

    public EnderecoDTO findById(Long id) {
        try{
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Endereco nao existe" + id));
            return toClienteDTO(endereco);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o endereco."+ex.getMessage(), ex);
        }
    }

    public List<EnderecoDTO> listar(){
        return enderecoRepository.findByAtivo().stream().map(this::toClienteDTO).toList();
    }

    public Endereco cadastrar(EnderecoDTO enderecoDTO){
        Endereco enderecoNovo = modelMapper.map(enderecoDTO,Endereco.class);
        return enderecoRepository.save(enderecoNovo);
    }

    public Endereco editar(Long id, EnderecoDTO enderecoDTO) {
        if (enderecoRepository.existsById(id)) {
            Endereco enderecoBanco = enderecoRepository.findById(id).orElseThrow(()
                    -> new IllegalArgumentException("Endereco não encontrado com o ID fornecido: " + id));

            enderecoBanco.setNumero(enderecoDTO.getNumero());
            enderecoBanco.setRua(enderecoDTO.getRua());
            enderecoBanco.setReferencia(enderecoDTO.getReferencia());
            enderecoBanco.setCliente(enderecoDTO.getCliente());

            return enderecoRepository.save(enderecoBanco);
        } else {
            throw new IllegalArgumentException("Endereco não encontrado com o ID fornecido: " + id);
        }
    }


    public void delete(Long id) {
        Endereco enderecoBanco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço com ID "+id +" nao existe"));

        if (enderecoBanco.getCliente().getPedidos().isEmpty()){
            desativarCliente(enderecoBanco);
        }else {
            throw new IllegalArgumentException("Endereco tem Pedido em andamento nao pode ser deletado");
        }
    }

    private void desativarCliente(Endereco endereco) {
        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

    public EnderecoDTO toClienteDTO(Endereco endereco) {
        return modelMapper.map(endereco, EnderecoDTO.class);
    }
}
