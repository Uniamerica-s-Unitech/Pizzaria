package Pizzaria.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String role;
    private String password;
    private Boolean ativo = true;

}