package Pizzaria.Controller;

import Pizzaria.Entiny.ClientePedido;
import Pizzaria.Service.ClientePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ClientePedido")
public class ClientePedidoController {

    @Autowired
    private ClientePedidoService service;

    @GetMapping
    public ResponseEntity<List<ClientePedido>> findAll(){
        try{
            return ResponseEntity.ok(service.findAll());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
