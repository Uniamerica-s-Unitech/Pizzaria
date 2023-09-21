package Pizzaria.Controller;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Service.ClienteService;
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

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/lista")
    public List<ClienteDTO> listar(){
        return clienteService.listar();
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody ClienteDTO clienteDTO) {
        try {
            clienteService.cadastrar(clienteDTO);
            return ResponseEntity.ok("Cadastrado com Sucesso");

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable Long id, @RequestBody @Validated ClienteDTO clienteDTO) {
        try {
            Cliente clienteEditado = clienteService.editar(id, clienteDTO);
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
            clienteService.delete(id);
            return ResponseEntity.ok().body("Cliente dezativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }

}
