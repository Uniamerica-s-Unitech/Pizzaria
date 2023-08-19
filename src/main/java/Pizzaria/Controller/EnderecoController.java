package Pizzaria.Controller;

import Pizzaria.Entiny.Endereco;
import Pizzaria.Service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public ResponseEntity<List<Endereco>> findAll(){
        try{
            return ResponseEntity.ok(service.findAll());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Endereco endereco){
        try{
            this.service.cadastrar(endereco);
            return ResponseEntity.ok("Endereço cadastrado com sucesso");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id,@RequestBody Endereco endereco){
        try {
            Endereco enderecoAtualizado = service.editar(id, endereco);
            if (enderecoAtualizado != null){
                return ResponseEntity.ok("Endereço atualizado com sucesso");
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id){
        try{
            service.deletar(id);
            return ResponseEntity.ok("Endereço deletado com sucesso");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
