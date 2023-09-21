package Pizzaria.Service;

import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.SaborRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaborService {
    private final SaborRepository saborRepository;
    private final ModelMapper modelMapper ;

    @Autowired
    public SaborService(SaborRepository saborRepository,
                          ModelMapper modelMapper){
        this.saborRepository = saborRepository;
        this.modelMapper = modelMapper;
    }

    public SaborDTO findById(Long id) {
        try{
            Sabor sabor = saborRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(" nao existe" + id));
            return toSaborDTO(sabor);
        }catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Ocorreu um erro ao tentar recuperar o cliente."+ex.getMessage(), ex);
        }
    }

    public List<SaborDTO> listar(){
        return saborRepository.findByAtivo().stream().map(this::toSaborDTO).toList();
    }

    public Sabor cadastrar(SaborDTO saborDTO){
        Sabor clienteNovo = modelMapper.map(saborDTO,Sabor.class);
        return saborRepository.save(clienteNovo);
    }

    public Sabor editar(Long id, SaborDTO saborDTO) {
        if (saborRepository.existsById(id)) {
            Sabor saborBanco = saborRepository.findById(id).orElseThrow(()
                    -> new IllegalArgumentException("Sabor não encontrado com o ID fornecido: " + id));

            saborBanco.setNome(saborDTO.getNome());

            return saborRepository.save(saborBanco);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado com o ID fornecido: " + id);
        }
    }


    public void delete(Long id) {
        Sabor saborBanco = saborRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sabor com ID "+id+" nao existe"));

        if (saborBanco != null) {
            desativarSabor(saborBanco);
        }
    }

    private void desativarSabor(Sabor sabor) {
        sabor.setAtivo(false);
        saborRepository.save(sabor);
    }


    public SaborDTO toSaborDTO(Sabor sabor) {
        return modelMapper.map(sabor, SaborDTO.class);
    }


}
