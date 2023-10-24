package Pizzaria.Controller;

import Pizzaria.DTO.MensagemDTO;
import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.findProdutoById(id);
        if (produtoDTO != null) {
            return ResponseEntity.ok(produtoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public List<ProdutoDTO> listar(){
        return produtoService.listar();
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        try{
            return ResponseEntity.ok(produtoService.cadastrarProduto(produtoDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemDTO> editarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            return ResponseEntity.ok(produtoService.editarProduto(id, produtoDTO));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemDTO> deletar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(produtoService.deletar(id));
        }catch(Exception e){
            MensagemDTO mensagem = new MensagemDTO(e.getMessage(),HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(mensagem);
        }
    }
}