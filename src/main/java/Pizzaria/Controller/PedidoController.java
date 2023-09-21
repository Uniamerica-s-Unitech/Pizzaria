package Pizzaria.Controller;

import Pizzaria.DTO.PedidoDTO;
import Pizzaria.Entiny.Pedido;
import Pizzaria.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/lista")
    public List<PedidoDTO> listar(){
        return pedidoService.listar();
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody PedidoDTO pedidoDTO){
        try {
            pedidoService.cadastrar(pedidoDTO);
            return ResponseEntity.ok("Com Sucesso");

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /*@PutMapping("/{id}")
    public PedidoDTO editar(@PathVariable Long id,@RequestBody PedidoDTO pedidoDTO){
        return pedidoService.editar(id, pedidoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            Pedido pedido = pedidoService.findById(id);
            if (pedido != null) {
                pedidoService.dezAtivar(id, pedido);
                return ResponseEntity.ok().body("Pedido desativado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }*/
}
