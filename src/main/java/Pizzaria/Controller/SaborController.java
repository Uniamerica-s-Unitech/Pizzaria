package Pizzaria.Controller;


import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sabor")
public class SaborController {

    @Autowired
    private SaborService saborService;

    @GetMapping("/lista")
    public List<SaborDTO> listar(){
        return saborService.listar();
    }

    @PostMapping
    public SaborDTO cadastrar(@RequestBody SaborDTO saborDTO){
        return saborService.cadastrar(saborDTO);
    }

    @PutMapping("/{id}")
    public SaborDTO editar(@PathVariable Long id,@RequestBody SaborDTO saborDTO){
        return saborService.editar(id, saborDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            Sabor sabor = saborService.findById(id);
            if (sabor != null) {
                saborService.dezAtivar(id, sabor);
                return ResponseEntity.ok().body("Sabor desativado com sucesso!");
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
