package Pizzaria.Repositorye;

import Pizzaria.Entiny.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaborRepository extends JpaRepository<Sabor,Long> {
}
