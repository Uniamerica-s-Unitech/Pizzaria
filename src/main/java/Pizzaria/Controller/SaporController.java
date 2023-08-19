package Pizzaria.Controller;


import Pizzaria.Entiny.Sapor;
import Pizzaria.Service.SaporService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sapor")
public class SaporController {

    @Autowired
    private SaporService service;

    @GetMapping
    public ResponseEntity<List<Sapor>> findAll(){
        try{
            return ResponseEntity.ok(service.findAll());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Sapor sapor){
        try{
            this.service.cadastrar(sapor);
            return ResponseEntity.ok("Sapor cadastrado com sucesso");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id,@RequestBody Sapor sapor){
        try {
            Sapor saporAtualizado = service.editar(id, sapor);
            if (saporAtualizado != null){
                return ResponseEntity.ok("Sapor atualizado com sucesso");
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
            return ResponseEntity.ok("Sapor deletado com sucesso");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
