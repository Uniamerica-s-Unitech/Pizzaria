package Pizzaria.Controller;

import Pizzaria.DTO.EnderecoDTO;
import Pizzaria.Entiny.Endereco;
import Pizzaria.Service.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Long id) {
        EnderecoDTO enderecoDTO = enderecoService.findEnderecoById(id);
        if (enderecoDTO != null) {
            return ResponseEntity.ok(enderecoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public List<EnderecoDTO> listar(){
        return enderecoService.listar();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        try{
            return ResponseEntity.ok(enderecoService.cadastrarEndereco(enderecoDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        try {
            return ResponseEntity.ok(enderecoService.editarEndereco(id, enderecoDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

   /* @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            enderecoService.deletar(id);
            return ResponseEntity.ok("Endereco deletado com sucesso!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }*/
}
