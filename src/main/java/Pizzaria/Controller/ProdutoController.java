package Pizzaria.Controller;

import Pizzaria.DTO.ProdutoDTO;
import Pizzaria.Entiny.Produto;
import Pizzaria.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ProdutoDTO cadastrar(@RequestBody ProdutoDTO produtoDTO){
        return produtoService.cadastrar(produtoDTO);
    }

    @PutMapping("/{id}")
    public ProdutoDTO editar(@PathVariable Long id,@RequestBody ProdutoDTO produtoDTO){
        return produtoService.editar(id, produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            Produto produto = produtoService.findById(id);
            if (produto != null) {
                produtoService.dezAtivar(id, produto);
                return ResponseEntity.ok().body("Produto desativado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
