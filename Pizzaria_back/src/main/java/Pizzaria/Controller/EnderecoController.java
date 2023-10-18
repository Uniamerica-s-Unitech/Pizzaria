package Pizzaria.Controller;

import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody EnderecoDTO enderecoDTO){
        try {
            enderecoService.cadastrar(enderecoDTO);
            return ResponseEntity.ok("Cadastrado com Sucesso");

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable Long id, @RequestBody @Validated EnderecoDTO enderecoDTO) {
        try {
            Endereco enderecoEditado = enderecoService.editar(id, enderecoDTO);
            return ResponseEntity.ok("O cadastro foi atualizado com sucesso.");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            enderecoService.delete(id);
            return ResponseEntity.ok().body("Endereço dezativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
