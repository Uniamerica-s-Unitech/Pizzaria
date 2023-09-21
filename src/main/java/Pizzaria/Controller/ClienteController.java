package Pizzaria.Controller;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Entiny.Cliente;
import Pizzaria.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return ResponseEntity.ok("Com Sucesso");

        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

/*    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        if (!id.equals(clienteDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(clienteService.updateCliente(clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dezAtivar(@PathVariable Long id){
        try {
            Cliente cliente = clienteService.findById(id);
            if (cliente != null) {
                clienteService.dezAtivar(id, cliente);
                return ResponseEntity.ok().body("Cliente desativado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
        }
    }*/

}
