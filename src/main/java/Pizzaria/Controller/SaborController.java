package Pizzaria.Controller;


import Pizzaria.Entiny.Sabor;
import Pizzaria.Service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sapor")
public class SaborController {

    @Autowired
    private SaborService service;

    @GetMapping
    public ResponseEntity<List<Sabor>> findAll(){
        try{
            return ResponseEntity.ok(service.findAll());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Sabor sabor){
        try{
            this.service.cadastrar(sabor);
            return ResponseEntity.ok("SaporDTO cadastrado com sucesso");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id,@RequestBody Sabor sabor){
        try {
            Sabor saborAtualizado = service.editar(id, sabor);
            if (saborAtualizado != null){
                return ResponseEntity.ok("SaporDTO atualizado com sucesso");
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
            return ResponseEntity.ok("SaporDTO deletado com sucesso");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
