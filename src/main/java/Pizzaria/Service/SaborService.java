package Pizzaria.Service;

import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Repositorye.SaborRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaborService {

    @Autowired
    private SaborRepository saborRepository;

    public Sabor findById(Long id){
        return saborRepository.findById(id).orElse(null);
    }

    public List<SaborDTO> listar(){
        List<Sabor> sabors = saborRepository.findByAtivo();
        return sabors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SaborDTO cadastrar(SaborDTO saborDTO){
        Sabor sabor = new Sabor();
        BeanUtils.copyProperties(saborDTO,sabor);
        sabor = saborRepository.save(sabor);
        return convertToDTO(sabor);
    }

    public SaborDTO editar(Long id,SaborDTO saborDTO){
        if (saborRepository.existsById(id)){
            Sabor sabor = saborRepository.findById(id).orElse(null);
            if (sabor != null){
                BeanUtils.copyProperties(saborDTO,sabor,"id");
                sabor = saborRepository.save(sabor);
                return convertToDTO(sabor);
            }
        }else {
            throw new IllegalArgumentException("Sabor não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void dezAtivar(Long id, Sabor sabor) {
        Sabor saborBanco = saborRepository.findById(id).orElse(null);
        if (saborBanco != null){
            sabor.setAtivo(false);
            saborRepository.save(sabor);
        }else {
            throw new IllegalArgumentException("Sabor não encontrado com o ID: " + id);
        }
    }

    private SaborDTO convertToDTO(Sabor sabor) {
        SaborDTO saborDTO = new SaborDTO();
        BeanUtils.copyProperties(sabor, saborDTO);
        return saborDTO;
    }
}
