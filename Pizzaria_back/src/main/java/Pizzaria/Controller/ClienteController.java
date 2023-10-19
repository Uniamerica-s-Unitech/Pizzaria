package Pizzaria.Controller;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.findCleinteById(id);
        if (clienteDTO != null) {
            return ResponseEntity.ok(clienteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/lista")
    public List<ClienteDTO> listar(){
        return clienteService.listar();
    }

    @PostMapping
    public ResponseEntity<String> cadastrarCleinte(@RequestBody ClienteDTO clienteDTO) {
        try{
            return ResponseEntity.ok(clienteService.cadastrarCliente(clienteDTO));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            return ResponseEntity.ok(clienteService.editarCliente(id, clienteDTO));
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
            clienteService.deletar(id);
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
