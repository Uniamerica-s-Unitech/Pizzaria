package Pizzaria.Controller;


import Pizzaria.DTO.ClienteDTO;
import Pizzaria.DTO.SaborDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Entiny.Sabor;
import Pizzaria.Service.ClienteService;
import Pizzaria.Service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<String> cadastrar(@RequestBody SaborDTO saborDTO) {
        try {
            saborService.cadastrar(saborDTO);
            return ResponseEntity.ok("Cadastrado com Sucesso");

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable Long id, @RequestBody @Validated SaborDTO saborDTO) {
        try {
            Sabor saborEditado = saborService.editar(id, saborDTO);
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
            saborService.delete(id);
            return ResponseEntity.ok().body("Sabor dezativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }
}
