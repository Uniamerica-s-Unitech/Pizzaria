package Pizzaria.Controller;

import Pizzaria.DTO.CategoriaDTO;
import Pizzaria.DTO.MensagemDTO;
import Pizzaria.Service.CategoriaServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {
    @Autowired
    CategoriaServices categoriaServices;

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        CategoriaDTO categoriaDTO = categoriaServices.findCategoriaById(id);
        if (categoriaDTO != null) {
            return ResponseEntity.ok(categoriaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/lista")
    public List<CategoriaDTO> listar(){
        return categoriaServices.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try{
            return ResponseEntity.ok(categoriaServices.cadastrarCategoria(categoriaDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        try {
            return ResponseEntity.ok(categoriaServices.editarCategoria(id, categoriaDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoriaServices.deletar(id));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }


}
