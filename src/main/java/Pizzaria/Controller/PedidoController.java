package Pizzaria.Controller;

import Pizzaria.Entiny.Pedido;
import Pizzaria.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll(){
        try{
            return ResponseEntity.ok(service.findAll());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Pedido pedido){
        try{
            this.service.cadastrar(pedido);
            return ResponseEntity.ok("Pedido cadastrado com sucesso");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id,@RequestBody Pedido pedido){
        try {
            Pedido pedidoAtualizado = service.editar(id, pedido);
            if (pedidoAtualizado != null){
                return ResponseEntity.ok("Pedido atualizado com sucesso");
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
            return ResponseEntity.ok("Pedido deletado com sucesso");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
}
