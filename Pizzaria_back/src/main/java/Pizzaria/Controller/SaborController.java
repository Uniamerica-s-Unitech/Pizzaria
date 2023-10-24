package Pizzaria.Controller;

import Pizzaria.DTO.MensagemDTO;
import Pizzaria.DTO.SaborDTO;
import Pizzaria.Service.SaborService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sabor")
@CrossOrigin(origins = "http://localhost:4200")
public class SaborController {
    @Autowired
    private SaborService saborService;

    @GetMapping("/{id}")
    public ResponseEntity<SaborDTO> buscarPorId(@PathVariable Long id) {
        SaborDTO saborDTO = saborService.findSaborById(id);
        if (saborDTO != null) {
            return ResponseEntity.ok(saborDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public List<SaborDTO> listar(){
        return saborService.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarSabor(@RequestBody SaborDTO saborDTO) {
        try{
            return ResponseEntity.ok(saborService.cadastrarSabor(saborDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarSabor(@PathVariable Long id, @RequestBody SaborDTO saborDTO) {
        try {
            return ResponseEntity.ok(saborService.editarSabor(id, saborDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            saborService.deletar(id);
            return ResponseEntity.ok(saborService.deletar(id));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}
