package Pizzaria.Controller;

import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Produto;
import Pizzaria.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/lista")
    public List<ProdutoDTO> listar(){
        return produtoService.listar();
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody ProdutoDTO produtoDTO) {
        try {
            produtoService.cadastrar(produtoDTO);
            return ResponseEntity.ok("Cadastrado com Sucesso");

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable Long id, @RequestBody @Validated ProdutoDTO produtoDTO) {
        try {
            Produto produtoEditado = produtoService.editar(id, produtoDTO);
            return ResponseEntity.ok("O cadastro foi atualizado com sucesso.");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocorreu um erro durante a atualização: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            produtoService.delete(id);
            return ResponseEntity.ok().body("Produto dezativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }*/
}
