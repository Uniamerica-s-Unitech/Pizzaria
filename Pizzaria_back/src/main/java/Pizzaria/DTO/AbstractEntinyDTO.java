package Pizzaria.DTO;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class AbstractEntinyDTO {
    private Long id;

    private Boolean ativo;

    @PrePersist
    private void prePersist(){
        this.ativo = true;
    }
}
