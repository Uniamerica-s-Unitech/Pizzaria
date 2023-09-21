package Pizzaria.Service;

import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Repositorye.ClienteRepository;
import Pizzaria.Repositorye.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;


    public Endereco findById(Long id){
        return enderecoRepository.findById(id).orElse(null);
    }

    public List<EnderecoDTO> listar(){
        List<Endereco> enderecos = enderecoRepository.findByAtivo();
        return enderecos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public String cadastrar(EnderecoDTO enderecoDTO){


        /*Assert.notNull(endereco.getCliente(), "Cliente inválido");
        Cliente cliente = clienteRepository.findById(enderecoDTO.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
*/

        return "com sucesso";
    }

    public EnderecoDTO editar(Long id,EnderecoDTO enderecoDTO){
        if (enderecoRepository.existsById(id)){
            Endereco endereco = enderecoRepository.findById(id).orElse(null);
            if (endereco != null){
                BeanUtils.copyProperties(enderecoDTO,endereco,"id");
                endereco = enderecoRepository.save(endereco);
                return convertToDTO(endereco);
            }
        }else {
            throw new IllegalArgumentException("Endereço não encontrado com o ID fornecido: " + id);
        }
        return null;
    }

    public void dezAtivar(Long id, Endereco endereco) {
        Endereco enderecoBanco = enderecoRepository.findById(id).orElse(null);
        if (enderecoBanco != null){
            endereco.setAtivo(false);
            enderecoRepository.save(endereco);
        }else {
            throw new IllegalArgumentException("Endereço não encontrado com o ID: " + id);
        }
    }

    private EnderecoDTO convertToDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setRua(endereco.getRua());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setCliente(endereco.getCliente());
        return enderecoDTO;
    }
}
