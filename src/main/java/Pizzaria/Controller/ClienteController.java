package Pizzaria.Controller;

import Pizzaria.DTO.ClienteDTO;
import Pizzaria.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ClienteDTO cadastrar(@RequestBody ClienteDTO clienteDTO){
        return clienteService.cadastrar(clienteDTO);
    }

    @PutMapping("/{id}")
    public ClienteDTO editar(@PathVariable Long id,@RequestBody ClienteDTO clienteDTO){
        return clienteService.editar(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
