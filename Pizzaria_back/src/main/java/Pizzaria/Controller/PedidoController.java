package Pizzaria.Controller;

import Pizzaria.DTO.MensagemDTO;
import Pizzaria.DTO.PedidoDTO;
import Pizzaria.Service.PedidoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        PedidoDTO pedidoDTO = pedidoService.findPedidoById(id);
        if (pedidoDTO != null) {
            return ResponseEntity.ok(pedidoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/abertos")
    public List<PedidoDTO> buscarTicketsAbertos(){
        return pedidoService.buscarTicketsAbertos();
    }
    @GetMapping("/historico")
    public List<PedidoDTO> buscarHistorico() {
        return pedidoService.buscarHistorico();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarPedido(@RequestBody PedidoDTO pedidoDTO) {
        try{
            return ResponseEntity.ok(pedidoService.cadastrarPedido(pedidoDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        try {
            return ResponseEntity.ok(pedidoService.editarPedido(id, pedidoDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}
