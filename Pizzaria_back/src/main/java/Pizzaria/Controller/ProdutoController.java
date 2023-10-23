package Pizzaria.Controller;

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
    public ResponseEntity<String> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        try{
            return ResponseEntity.ok(produtoService.cadastrarProduto(produtoDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            return ResponseEntity.ok(produtoService.editarProduto(id, produtoDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.ok("Produto deletado com sucesso!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}