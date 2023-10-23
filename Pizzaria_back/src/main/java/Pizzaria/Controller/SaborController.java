package Pizzaria.Controller;

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
    public ResponseEntity<String> cadastrarSabor(@RequestBody SaborDTO saborDTO) {
        try{
            return ResponseEntity.ok(saborService.cadastrarSabor(saborDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarSabor(@PathVariable Long id, @RequestBody SaborDTO saborDTO) {
        try {
            return ResponseEntity.ok(saborService.editarSabor(id, saborDTO));
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
            saborService.deletar(id);
            return ResponseEntity.ok("Sabor deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
