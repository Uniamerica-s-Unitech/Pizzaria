package Pizzaria.Controller;


import Pizzaria.DTO.CategoriaDTO;
import Pizzaria.Service.CategoriaServices;
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
    public ResponseEntity<String> cadastrarCleinte(@RequestBody CategoriaDTO categoriaDTO) {
        try{
            return ResponseEntity.ok(categoriaServices.cadastrarCategoria(categoriaDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarCliente(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        try {
            return ResponseEntity.ok(categoriaServices.edtitarCategoria(id, categoriaDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

   /* @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            categoriaServices.deletar(id);
            return ResponseEntity.ok("Aluno deletado com sucesso!");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }*/


}
