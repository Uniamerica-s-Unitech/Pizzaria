package Pizzaria.Controller;

import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/lista")
    public List<EnderecoDTO> listar(){
        return enderecoService.listar();
    }

    /*@PostMapping
    public EnderecoDTO cadastrar(@RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.cadastrar(enderecoDTO);
    }*/

    @PutMapping("/{id}")
    public EnderecoDTO editar(@PathVariable Long id,@RequestBody EnderecoDTO enderecoDTO){
        return enderecoService.editar(id, enderecoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            Endereco endereco = enderecoService.findById(id);
            if (endereco != null) {
                enderecoService.dezAtivar(id, endereco);
                return ResponseEntity.ok().body("Endereço desativado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
