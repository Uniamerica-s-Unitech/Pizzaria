package Pizzaria.Controller;

import Pizzaria.DTO.ClientePedidoDTO;
import Pizzaria.Service.ClientePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientepedido")
public class ClientePedidoController {

    @Autowired
    private ClientePedidoService clientePedidoService;

    @GetMapping("/lista")
    public List<ClientePedidoDTO> listar(){
        return clientePedidoService.listar();
    }

    @PostMapping
    public ClientePedidoDTO cadastrar(@RequestBody ClientePedidoDTO clientePedidoDTO){
        return clientePedidoService.cadastrar(clientePedidoDTO);
    }
}
