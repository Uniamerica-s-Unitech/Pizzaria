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

}
